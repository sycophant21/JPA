package com.Managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    private static ConnectionManager connectionManager = null;
    private final String JDBC_DRIVER;
    private final String DB_URL;
    private final String USER;
    private final String PASS;
    private Connection conn;

    private ConnectionManager() {
        JDBC_DRIVER = "";
        DB_URL = "";
        USER = "";
        PASS = "";
        conn = establishConnection();
    }

    public static ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    private Connection establishConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception se) {
            se.printStackTrace();
        }
        return conn;
    }

    public Statement getStatement() {
        try {
            return conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
