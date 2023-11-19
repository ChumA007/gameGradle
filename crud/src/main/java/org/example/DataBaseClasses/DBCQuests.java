package org.example.DataBaseClasses;

import org.example.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBCQuests extends DBC{

    public DBCQuests(Connection connection) {
        super(connection);
    }


    public void addQuest(int id, String name, String description, int deadline) throws SQLException {
        String sql = "INSERT INTO quests (id, name, description, deadline) VALUES (?,?,?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setInt(4, deadline);
            statement.executeUpdate();
            System.out.println("Успешно");
        }
    }

    public void addQuests(Player player) throws SQLException {
        String sql = "INSERT INTO quests (id, name, description, deadline, isCompleted) VALUES (?,?,?,?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            for (Quest q : player.getQuests().getQuests()) {
                statement.setInt(1, player.getId());
                statement.setString(2, q.getName());
                statement.setString(3, q.getDescription());
                statement.setInt(4, q.getDeadline());
                statement.setBoolean(5, q.isCompleted());
                statement.executeUpdate();
                System.out.println("Успешно");
            }
        }
    }

    public List<Quest> findQuestsById(int id) throws SQLException {
        String sql = "SELECT * FROM quests WHERE id = ?";
        List<Quest> quests = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            DBCPlayers dbcPlayers = new DBCPlayers(getConnection());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Player player = dbcPlayers.findPlayerById(id);
                    quests = player.getQuests().getQuests();
                }
            }
        }
        return quests;
    }

    public void updateQuests(boolean isCompleted, int id, String name) throws SQLException {
        String sql = "UPDATE quests SET isCompleted = ? WHERE id = ? AND name = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setBoolean(5, isCompleted);
            statement.executeUpdate();
        }
    }

    @Override
    public List<Quest> getAll() throws SQLException {
        String sql = "SELECT * FROM skills";
        List<Quest> quests = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Quest quest = new Quest(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("description"), resultSet.getInt("deadline"),
                        resultSet.getBoolean("isCompleted"));
                quests.add(quest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quests;
    }
}
