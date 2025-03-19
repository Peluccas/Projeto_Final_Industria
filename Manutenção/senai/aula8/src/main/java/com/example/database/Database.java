package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/monitoramento";
    private static final String USER = "root";  // Substitua pelo seu usuário
    private static final String PASSWORD = "root";  // Substitua pela sua senha

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
