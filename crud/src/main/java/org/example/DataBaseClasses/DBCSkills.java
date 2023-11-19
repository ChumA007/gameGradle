package org.example.DataBaseClasses;

import org.example.Level;
import org.example.Player;
import org.example.Skills;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBCSkills extends DBC{

    public DBCSkills(Connection connection) {
        super(connection);
    }


    public void addSkills(int id, int strength, int endurance, int perception) throws SQLException {
        String sql = "INSERT INTO skills (id, strength, endurance, perception) VALUES (?,?,?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, strength);
            statement.setInt(3, endurance);
            statement.setInt(4, perception);
            statement.executeUpdate();
            System.out.println("Успешно");
        }
    }

    public void addSkills(Player  player){
        String sql = "INSERT INTO skills (id, strength, endurance, perception) VALUES (?,?,?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, player.getId());
            statement.setInt(2, player.getSkills().getStrength());
            statement.setInt(3, player.getSkills().getEndurance());
            statement.setInt(4, player.getSkills().getPerception());
            statement.executeUpdate();
            System.out.println("Умения добавлены");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Skills findSkillsById(int id) throws SQLException {
        String sql = "SELECT * FROM skills WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                DBCPlayers dbcPlayers = new DBCPlayers(getConnection());
                if (resultSet.next()) {
                    Player player = dbcPlayers.findPlayerById(resultSet.getInt("id"));
                    Skills skills = new Skills(player.getId(), player.getSkills().getStrength(), player.getSkills().getEndurance(),
                            player.getSkills().getPerception());
                    return skills;
                } else {
                    return null;
                }
            }
        }
    }

    public void updateSkills(int id, int strength, int endurance, int perception) throws SQLException {
        String sql = "UPDATE skills SET strength = ?, endurance = ?, perception = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, strength);
            statement.setInt(3, endurance);
            statement.setInt(4, perception);
            statement.executeUpdate();
        }
    }

    @Override
    public List getAll() throws SQLException {
        return null;
    }
}
