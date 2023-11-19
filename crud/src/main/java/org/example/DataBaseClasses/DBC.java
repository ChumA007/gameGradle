package org.example.DataBaseClasses;
import org.example.Connect;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class DBC<T> {

    private Connection connection;

    public DBC(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public abstract List<T> getAll() throws SQLException;
}
