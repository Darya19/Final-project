package com.epam.task1.model.connector;
import  java.lang.System.*;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorDB {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String password = "1234";
        return DriverManager.getConnection(url, user, password);
    }
}
