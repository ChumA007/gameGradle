package org.example.DataBaseClasses;

import org.example.Level;
import org.example.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBCLevel extends DBC{
    public DBCLevel(Connection connection) {
        super(connection);
    }


    public void addLevel(int id, int level, int experience) throws SQLException {
        String sql = "INSERT INTO level (id, level, experience) VALUES (?,?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, level);
            statement.setInt(3, experience);
            statement.executeUpdate();
            System.out.println("Успешно");
        }
    }

    public Level findLevelById(int id) throws SQLException {
        String sql = "SELECT * FROM level WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                DBCPlayers dbcPlayers = new DBCPlayers(getConnection());
                if (resultSet.next()) {
                    Player player = dbcPlayers.findPlayerById(resultSet.getInt("id"));
                    Level level = new Level(player.getId(), player.getLevel().getLevel(), player.getLevel().getExperience());
                    return level;
                } else {
                    return null;
                }
            }
        }
    }

    public void updateLevel(int id, int level, int experience) throws SQLException {
        String sql = "UPDATE level SET level = ?, experience = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, level);
            statement.setInt(3, experience);
            statement.executeUpdate();
        }
    }

    @Override
    public List getAll() throws SQLException {
        return null;
    }
}
