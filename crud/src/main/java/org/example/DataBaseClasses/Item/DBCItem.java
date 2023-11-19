package org.example.DataBaseClasses.Item;

import org.example.DataBaseClasses.DBC;
import org.example.items.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBCItem extends DBC {

    public DBCItem(Connection connection) {
        super(connection);
    }

    @Override
    public List getAll() throws SQLException {
        return null;
    }

    public void addItem(int itemId, String name, double weight, int width, int length, int durability) throws SQLException {
        String sql = "INSERT INTO item (itemId, name, weight, width, length, durability) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, itemId);
            statement.setString(2, name);
            statement.setDouble(3, weight);
            statement.setInt(4, width);
            statement.setInt(5, length);
            statement.setInt(6, durability);
            statement.executeUpdate();
            System.out.println("Успешно");
        }
    }

    public void addItem(List<Item> list) throws SQLException {
        String sql = "INSERT INTO item (itemId, name, weight, width, length, durability) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            int id = 0;
            for (Item i : list) {
                if(id != i.getItemId()) {
                    statement.setInt(1, i.getItemId());
                    statement.setString(2, i.getName());
                    statement.setDouble(3, i.getWeight());
                    statement.setInt(4, i.getWidth());
                    statement.setInt(5, i.getLength());
                    statement.setInt(6, i.getDurability());
                    statement.executeUpdate();
                    System.out.println("Item created");
                    id = i.getItemId();
                }
            }
        }
    }

    public void addItem(Item item) throws SQLException {
        String sql = "INSERT INTO item (itemId, name, weight, width, length, durability) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
                statement.setInt(1, item.getItemId());
                statement.setString(2, item.getName());
                statement.setDouble(3, item.getWeight());
                statement.setInt(4, item.getWidth());
                statement.setInt(5, item.getLength());
                statement.setInt(6, item.getDurability());
                statement.executeUpdate();
                System.out.println("Item created");
        }
    }

    public Item findItemById(int id) throws SQLException {
        String sql = "SELECT * FROM item WHERE itemId = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Item item = new Item(resultSet.getInt("itemId"), resultSet.getString("name"), resultSet.getDouble("weight"),
                            resultSet.getInt("width"), resultSet.getInt("length"), resultSet.getInt("durability"));
                    return item;
                } else {
                    return null;
                }
            }
        }
    }

    public int findItemByName(String name){
        String sql = "SELECT * FROM item WHERE name = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("itemId");
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateItem(Item item) throws SQLException {
        String sql = "UPDATE item SET name = ?, weight = ?, width = ?, length = ?, durability = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getWeight());
            statement.setInt(3, item.getWidth());
            statement.setInt(4, item.getLength());
            statement.setInt(5, item.getDurability());
            statement.setInt(6, item.getItemId());
            statement.executeUpdate();
        }
    }

    public void deleteItem(int itemId) throws SQLException {
        String sql = "DELETE FROM item WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, itemId);
            statement.executeUpdate();
        }
    }
}
