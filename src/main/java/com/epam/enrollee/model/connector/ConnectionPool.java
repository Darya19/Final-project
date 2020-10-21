package com.epam.enrollee.model.connector;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public enum ConnectionPool {

    INSTANCE;

    private static final int DEFAULT_POOL_SIZE = 4;
    private BlockingQueue<ProxyConnection> freeConnections; //свободные соеденения
    private BlockingQueue<ProxyConnection> givenAwayConnections; // отданные соеденнения

    private static Logger logger = LogManager.getLogger();

    ConnectionPool() {
        this.freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        this.givenAwayConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String password = resource.getString("db.password");
        String driverName = resource.getString("db.driverName");
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                freeConnections.offer(new ProxyConnection(connection));
            }
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException("impossible register db driver", e);
        } catch (SQLException e) {
            throw new RuntimeException("impossible create connection to db", e);
        }
    }


    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "impossible provide connection");
        }
        return connection;
    }

    public void realiseConnection(Connection connection) {
        if (connection.getClass().equals(ProxyConnection.class)) {
            if (givenAwayConnections.remove(connection)) {
                freeConnections.offer((ProxyConnection) connection);
            } else {
                logger.log(Level.ERROR, "pool does not include given connection");
            }
        } else {
            logger.log(Level.ERROR, "given connection is not a proxy connection");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (SQLException | InterruptedException e) {
                logger.log(Level.ERROR, "impossible close connection", e);
            }
        }
        deregisterDrivers();
    }

    public void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                logger.log(Level.ERROR, "impossible deregister driver", e);
            }
        }
    }//TODO timer task поток для досоздания или удаления коннекшенов
}
