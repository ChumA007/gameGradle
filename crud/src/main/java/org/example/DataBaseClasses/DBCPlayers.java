package org.example.DataBaseClasses;


import org.example.*;
import org.example.DataBaseClasses.Item.DBCItem;
import org.example.DataBaseClasses.Item.DBCWeapon;
import org.example.items.Item;
import org.example.items.MedicalKit;
import org.example.items.Weapon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DBCPlayers extends DBC {

    public DBCPlayers(Connection connection) {
        super(connection);
    }

    public void addPlayer(String nickname) throws SQLException {
        String sql = "INSERT INTO players (nickname) VALUES (?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, nickname);
            statement.executeUpdate();
            System.out.println("Успешно");
        }
    }

    public void addPlayer(Player player) throws SQLException {
        String sql = "INSERT INTO players (id, nickname) VALUES (?,?)";
        List<String> list = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, player.getId());
            statement.setString(2, player.getNickname());
            list.add(player.getNickname());
            statement.executeUpdate();
            System.out.println("Player created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBCInventory dbcInventory = new DBCInventory(getConnection());
        dbcInventory.addInventory(player);
        DBCBalance dbcBalance = new DBCBalance(getConnection());
        dbcBalance.addBalance(player);
        DBCHealth dbcHealth = new DBCHealth(getConnection());
        dbcHealth.addHealth(player.getId(), player.getHealth().getCurrentHealth(), player.getHealth().getMaxHealth());
        DBCLevel dbcLevel = new DBCLevel(getConnection());
        dbcLevel.addLevel(player.getId(), player.getLevel().getLevel(), player.getLevel().getExperience());
    }

    public void findPlayerByName(String name){
        String sql = "SELECT * FROM players WHERE nickname = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Player player = new Player(resultSet.getInt("id"), resultSet.getString("nickname"));
                    player.setQuests(getQuestsForPlayer(player.getId()));
                    player.setBalance(getBalanceForPlayer(player.getId()));
                    player.setHealth(getHealthForPlayer(player.getId()));
                    player.setInventory(getInventoryForPlayer(player.getId()));
                    player.setLevel(getLevelForPlayer(player.getId()));
                    player.setSkills(getSkillsForPlayer(player.getId()));
                    System.out.println(player.toString());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Player findPlayerById(int id) throws SQLException {
        String sql = "SELECT * FROM players WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Player player = new Player(resultSet.getInt("id"), resultSet.getString("nickname"),
                            resultSet.getObject("skills", Skills.class),
                            resultSet.getObject("quests", Quests.class),
                            resultSet.getObject("inventory", Inventory.class),
                            resultSet.getObject("level", Level.class),
                            resultSet.getObject("health", Health.class),
                            resultSet.getObject("balance", Balance.class));
                    return player;
                } else {
                    return null;
                }
            }
        }
    }

    public void updatePlayer(Player player) throws SQLException {
        String sql = "UPDATE players SET nickname = ?, skills = ?, quests = ?, inventory = ?, level = ?, health = ?, balance = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, player.getNickname());
            statement.setObject(2, player.getSkills());
            statement.setObject(3, player.getQuests());
            statement.setObject(4, player.getInventory());
            statement.setObject(5, player.getLevel());
            statement.setObject(6, player.getHealth());
            statement.setObject(7, player.getBalance());
            statement.setInt(8, player.getId());
            statement.executeUpdate();
        }
    }

    public void updatePlayer(int id, String name){
        String sql = "UPDATE players SET nickname = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePlayer(int id) throws SQLException {
        String sql = "DELETE FROM players WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    /*@Override
    public List<Player> getAll() {
        String sql = "SELECT * FROM players";
        List<Player> players = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Player player = new Player(resultSet.getInt("id"), resultSet.getString("nickname"));
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }*/

    @Override
    public List<Player> getAll() {
        String sql = "SELECT * FROM players";
        List<Player> players = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int playerId = resultSet.getInt("id");
                String nickname = resultSet.getString("nickname");

                // Получение списка квестов для игрока
                Quests quests = getQuestsForPlayer(playerId);

                // Получение списка навыков для игрока
                Skills skills = getSkillsForPlayer(playerId);

                // Получение инвентаря игрока
                Inventory inventory = getInventoryForPlayer(playerId);

                // Получение данных о здоровье игрока
                Health health = getHealthForPlayer(playerId);

                Level level = getLevelForPlayer(playerId);

                // Получение баланса игрока
                Balance balance = getBalanceForPlayer(playerId);

                Player player = new Player(playerId, nickname, skills, quests, inventory, level, health, balance);
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public Player getAllByName(String nick) {
        String sql = "SELECT * FROM players WHERE nickname = ?";
        Player player = null;
        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int playerId = resultSet.getInt("id");
                String nickname = resultSet.getString("nickname");

                // Получение списка квестов для игрока
                Quests quests = getQuestsForPlayer(playerId);

                // Получение списка навыков для игрока
                Skills skills = getSkillsForPlayer(playerId);

                // Получение инвентаря игрока
                Inventory inventory = getInventoryForPlayer(playerId);

                // Получение данных о здоровье игрока
                Health health = getHealthForPlayer(playerId);

                Level level = getLevelForPlayer(playerId);

                // Получение баланса игрока
                Balance balance = getBalanceForPlayer(playerId);

                player = new Player(playerId, nickname, skills, quests, inventory, level, health, balance);
                System.out.println(player.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    public Balance getBalanceForPlayer(int playerId) {
        String sql = "SELECT * FROM balance WHERE id = ?";
        Balance balance = null;
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    balance = new Balance(resultSet.getInt("id"));
                    do {
                        int moneyId = resultSet.getInt("moneyId");
                        int amount = resultSet.getInt("amount");
                        String name = resultSet.getString("name");// Метод для получения информации о монете по ее id
                        balance.addMoney(new Money(moneyId, amount, name));
                    } while (resultSet.next());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    private Level getLevelForPlayer(int playerId) {
        String sql = "SELECT * FROM level WHERE id = ?";
        Level lv = null;

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int level = resultSet.getInt("level");
                    int experience = resultSet.getInt("experience");

                    lv = new Level(id, level, experience);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lv;
    }

    private Health getHealthForPlayer(int playerId) {
        String sql = "SELECT * FROM health WHERE id = ?";
        Health health = null;

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int currentHealth = resultSet.getInt("currentHealth");
                    int maxHealth = resultSet.getInt("maxHealth");

                    health = new Health(id, currentHealth, maxHealth);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return health;
    }

    private Quests getQuestsForPlayer(int playerId) {
        String sql = "SELECT * FROM quests WHERE id = ?";
        Quests quests = new Quests(playerId);
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int questId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    int deadline = resultSet.getInt("deadline");
                    boolean isCompleted = resultSet.getBoolean("isCompleted");

                    Quest quest = new Quest(questId, name, description, deadline, isCompleted);
                    quests.addQuest(quest);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quests;
    }

    private Skills getSkillsForPlayer(int playerId) {
        String sql = "SELECT * FROM skills WHERE id = ?";
        Skills skills = null;

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int strength = resultSet.getInt("strength");
                    int endurance = resultSet.getInt("endurance");
                    int perception = resultSet.getInt("perception");

                    skills = new Skills(id, strength, endurance, perception);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return skills;
    }

    private Inventory getInventoryForPlayer(int playerId) {
        String sql = "SELECT item.* FROM inventory JOIN item ON inventory.itemId = item.itemId WHERE inventory.id = ?";
        Inventory inventory = new Inventory(playerId);
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int itemId = resultSet.getInt("itemId");
                    String name = resultSet.getString("name");
                    double weight = resultSet.getDouble("weight");
                    int width = resultSet.getInt("width");
                    int length = resultSet.getInt("length");
                    int durability = resultSet.getInt("durability");
                    if (resultSet.getInt("damage") > 0){
                        Weapon weapon = new Weapon(itemId, name, weight, width, length,
                                durability, resultSet.getInt("damage"));
                        inventory.addItem(weapon);
                    } else{
                        MedicalKit medicalKit = new MedicalKit(itemId, name, weight, width,
                                length, durability, resultSet.getInt("healingPower"));
                        inventory.addItem(medicalKit);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventory;
    }
}
