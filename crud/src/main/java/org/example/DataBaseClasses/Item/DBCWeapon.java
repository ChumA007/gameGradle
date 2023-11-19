package org.example.DataBaseClasses.Item;


import org.example.Balance;
import org.example.Connect;
import org.example.Money;
import org.example.Player;
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

public class DBCWeapon extends DBCItem{

    public DBCWeapon(Connection connection) {
        super(connection);
    }

    public void addWeapon(int itemId, String name, double weight, int width, int length, int durability, int damage) throws SQLException {
        DBCItem dbcItem = new DBCItem(getConnection());
        dbcItem.addItem(itemId, name, weight, width, length, durability);
        String sql = "INSERT INTO weapon (itemId, damage) VALUES (?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, itemId);
            statement.setInt(2, damage);
            statement.executeUpdate();
            System.out.println("Добавление успешно!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addWeapon(Weapon weapon) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM weapon WHERE itemId = ?";
        try (PreparedStatement checkStatement = getConnection().prepareStatement(checkSql)) {
            checkStatement.setInt(1, weapon.getItemId());
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                System.out.println("Предмет уже существует в таблице weapon");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql = "INSERT INTO weapon (itemId, damage) VALUES (?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, weapon.getItemId());
            statement.setInt(2, weapon.getDamage());
            statement.executeUpdate();
            System.out.println("Добавление успешно!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Weapon findWeaponById(int itemId){
        String sql = "SELECT * FROM weapon WHERE itemId = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, itemId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    if(findItemById(itemId) != null) {
                        Item item = findItemById(itemId);
                        Weapon weapon1 = new Weapon(itemId, item.getName(), item.getWeight(),
                                item.getWidth(), item.getLength(), item.getDurability(),
                                resultSet.getInt("damage"));
                        return weapon1;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Item getItemForWeapon(int itemId) {
        String sql = "SELECT * FROM item WHERE itemId = ?";
        Item item = null;
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, itemId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    item = new Item(itemId, resultSet.getString("name"), resultSet.getDouble("weigth"),
                            resultSet.getInt("width"), resultSet.getInt("length"), resultSet.getInt("durability"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public void updateWeapon(int id, int durability){
        String sql = "UPDATE weapon SET durability = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, durability);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateWeapon(Weapon weapon){
        String sql = "UPDATE weapon SET itemId = ?, name = ?, weigth = ?, width = ?, length = ?, durability = ?, damage = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, weapon.getItemId());
            statement.setString(2, weapon.getName());
            statement.setDouble(3, weapon.getWeight());
            statement.setInt(4, weapon.getWidth());
            statement.setInt(5, weapon.getLength());
            statement.setInt(6, weapon.getDurability());
            statement.setInt(7, weapon.getDamage());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteWeapon(int id) throws SQLException {
        String sql = "DELETE FROM weapon WHERE id = ?";
        int itemId = findWeaponById(id).getItemId();
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Delete weapon");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBCItem dbcItem = new DBCItem(getConnection());
        dbcItem.deleteItem(itemId);
    }

    @Override
    public List<Weapon> getAll() throws SQLException {
        String sql = "SELECT * FROM weapon";
        DBCItem dbcItem = new DBCItem(getConnection());
        List<Weapon> weapons = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String name = dbcItem.findItemById(resultSet.getInt("itemId")).getName();
                Weapon weapon = new Weapon(resultSet.getInt("itemId"), name,
                        resultSet.getDouble("weight"),
                        resultSet.getInt("width"),
                        resultSet.getInt("length"),
                        resultSet.getInt("durability"),
                        resultSet.getInt("damage"));
                weapons.add(weapon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weapons;
    }
}
