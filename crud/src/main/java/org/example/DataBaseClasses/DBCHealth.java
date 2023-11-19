package org.example.DataBaseClasses;

import org.example.Health;
import org.example.Level;
import org.example.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBCHealth extends DBC{

    public DBCHealth(Connection connection) {
        super(connection);
    }


    public void addHealth(int id, int currentHealth, int maxHealth) throws SQLException {
        String sql = "INSERT INTO health (id, currentHealth, maxHealth) VALUES (?,?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, currentHealth);
            statement.setInt(3, maxHealth);
            statement.executeUpdate();
            System.out.println("Успешно");
        }
    }

    public Health findHealthById(int id) throws SQLException {
        String sql = "SELECT * FROM health WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                DBCPlayers dbcPlayers = new DBCPlayers(getConnection());
                if (resultSet.next()) {
                    Player player = dbcPlayers.findPlayerById(resultSet.getInt("id"));
                    Health health = new Health(player.getId(), player.getHealth().getCurrentHealth(), player.getHealth().getMaxHealth());
                    return health;
                } else {
                    return null;
                }
            }
        }
    }

    public void updateHealth(Health health) throws SQLException {
        String sql = "UPDATE health SET currentHealth = ?, maxHealth = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, health.getId());
            statement.setString(2, String.valueOf(health.getCurrentHealth()));
            statement.setString(3, String.valueOf(health.getMaxHealth()));
            statement.executeUpdate();
        }
    }

    @Override
    public List getAll() throws SQLException {
        return null;
    }
}
