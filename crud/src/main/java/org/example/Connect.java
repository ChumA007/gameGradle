package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    public static Connection connector() throws SQLException {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/java-gradle--master";
        String username = "postgres";
        String password = "ChumaCh9870";
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        System.out.println("Connect!");
        return connection;
    }
}
