package org.example.DataBaseClasses.Item;

import org.example.items.Item;
import org.example.items.MedicalKit;
import org.example.items.Weapon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBCMedicalKit extends DBCItem{


    public DBCMedicalKit(Connection connection) {
        super(connection);
    }

    public void addMedicalKit(String name, double weight, int width, int length, int durability, int healingPower) throws SQLException {
        DBCItem dbcItem = new DBCItem(getConnection());
        int itemId = dbcItem.findItemByName(name);
        dbcItem.addItem(itemId, name, weight, width, length, durability);
        String sql = "INSERT INTO medicalKit (itemId, healingPower) VALUES (?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, itemId);
            statement.setInt(2, healingPower);
            statement.executeUpdate();
            System.out.println("Добавление успешно!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMedicalKit(MedicalKit medicalKit) throws SQLException {
        //DBCItem dbcItem = new DBCItem(getConnection());
        //int itemId = dbcItem.findItemByName(medicalKit.getName());
        //dbcItem.addItem(itemId, medicalKit.getName(), medicalKit.getWeight(), medicalKit.getWidth(), medicalKit.getLength(), medicalKit.getDurability());

        String sql = "INSERT INTO medicalKit (itemId, healingPower) VALUES (?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, medicalKit.getItemId());
            statement.setInt(2, medicalKit.getHealingPower());
            statement.executeUpdate();
            System.out.println("Добавление успешно!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MedicalKit findMedicalKitByName(String name){
        String sql = "SELECT * FROM medicalKit WHERE itemId = ?";
        DBCItem dbcItem = new DBCItem(getConnection());
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            int itemId = dbcItem.findItemByName(name);
            statement.setInt(1, itemId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    MedicalKit medicalKit = new MedicalKit(resultSet.getInt("itemId"), name,
                            resultSet.getDouble("weight"),
                            resultSet.getInt("width"),
                            resultSet.getInt("length"),
                            resultSet.getInt("durability"),
                            resultSet.getInt("healingPower"));
                    return medicalKit;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MedicalKit findMedicalKitById(int id){
        String sql = "SELECT * FROM medicalKit WHERE mdId = ?";
        DBCItem dbcItem = new DBCItem(getConnection());
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = dbcItem.findItemById(resultSet.getInt("itemId")).getName();
                    MedicalKit medicalKit = new MedicalKit(resultSet.getInt("itemId"), name,
                            resultSet.getDouble("weight"),
                            resultSet.getInt("width"),
                            resultSet.getInt("length"),
                            resultSet.getInt("durability"),
                            resultSet.getInt("healingPower"));
                    return medicalKit;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateMedicalKit(int id, int healingPower){
        String sql = "UPDATE medicalKit SET damage = ? WHERE mdId = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, healingPower);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MedicalKit findMedicalKitById1(int itemId){
        String sql = "SELECT * FROM medicalKit WHERE itemId = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, itemId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    if(findItemById(itemId) != null) {
                        Item item = findItemById(itemId);
                        MedicalKit medicalKit = new MedicalKit(itemId, item.getName(),
                                item.getWeight(), item.getWidth(), item.getLength(), item.getDurability(),
                                resultSet.getInt("healingPower"));
                        return medicalKit;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateMedicalKit(MedicalKit medicalKit){
        String sql = "UPDATE medicalKit SET itemId = ?, name = ?, weigth = ?, width = ?, length = ?, durability = ?, healingPower = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, medicalKit.getItemId());
            statement.setString(2, medicalKit.getName());
            statement.setDouble(3, medicalKit.getWeight());
            statement.setInt(4, medicalKit.getWidth());
            statement.setInt(5, medicalKit.getLength());
            statement.setInt(6, medicalKit.getDurability());
            statement.setInt(7, medicalKit.getHealingPower());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMedicalKit(int id) throws SQLException {
        String sql = "DELETE FROM medicalKit WHERE mdId = ?";
        int itemId = findMedicalKitById(id).getItemId();
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Набор первой помощи удалена!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBCItem dbcItem = new DBCItem(getConnection());
        dbcItem.deleteItem(itemId);
    }
    @Override
    public List<MedicalKit> getAll() throws SQLException {
        String sql = "SELECT * FROM medicalKit";
        DBCItem dbcItem = new DBCItem(getConnection());
        List<MedicalKit> medicalKits = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String name = dbcItem.findItemById(resultSet.getInt("itemId")).getName();
                MedicalKit medicalKit = new MedicalKit(resultSet.getInt("itemId"), name,
                        resultSet.getDouble("weight"),
                        resultSet.getInt("width"),
                        resultSet.getInt("length"),
                        resultSet.getInt("durability"),
                        resultSet.getInt("healingPower"));
                medicalKits.add(medicalKit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicalKits;
    }
}
