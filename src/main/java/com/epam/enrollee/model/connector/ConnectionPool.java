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

/**
 * The enum Connection pool.
 * The pool for connections to database.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public enum ConnectionPool {

    /**
     * Instance connection pool.
     */
    INSTANCE;

    private static final int DEFAULT_POOL_SIZE = 4;
    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> givenAwayConnections;

    private static Logger logger = LogManager.getLogger();

    ConnectionPool() {
        this.freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        this.givenAwayConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        String url = resourceBundle.getString("db.url");
        String user = resourceBundle.getString("db.user");
        String password = resourceBundle.getString("db.password");
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                freeConnections.offer(new ProxyConnection(connection));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Impossible create connection to db or register db driver", e);
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Impossible provide connection");
        }
        return connection;
    }

    /**
     * Realise connection.
     *
     * @param connection the connection
     */
    public void realiseConnection(Connection connection) {
        if (connection.getClass().equals(ProxyConnection.class)) {
            if (givenAwayConnections.remove(connection)) {
                freeConnections.offer((ProxyConnection) connection);
            } else {
                logger.log(Level.ERROR, "Pool does not include given connection");
            }
        } else {
            logger.log(Level.ERROR, "Given connection is not a proxy connection");
        }
    }

    /**
     * Destroy pool.
     */
    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (SQLException | InterruptedException e) {
                logger.log(Level.ERROR, "Impossible close connection", e);
            }
        }
        deregisterDrivers();
    }

    /**
     * Deregister drivers.
     */
    public void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Impossible deregister driver", e);
            }
        }
    }
}