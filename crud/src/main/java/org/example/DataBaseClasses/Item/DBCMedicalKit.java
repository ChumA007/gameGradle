package org.example.DataBaseClasses.Item;

import org.example.Connect;
import org.example.items.Item;
import org.example.items.MedicalKit;

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
            DBCItem dbcItem = new DBCItem(Connect.connector());
            Item item = dbcItem.findItemById(medicalKit.getItemId());
            if (item == null) {
                dbcItem.addItem(medicalKit.getItemId(), medicalKit.getName(), medicalKit.getWeight(), medicalKit.getWidth(),
                        medicalKit.getLength(), medicalKit.getDurability());
            }
            statement.setInt(1, medicalKit.getItemId());
            statement.setInt(2, medicalKit.getHealingPower());
            statement.executeUpdate();
            System.out.println("Добавление успешно!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MedicalKit findMedicalKitByName(String name){
        String sql = "SELECT * FROM MedicalKit WHERE itemId = ?";
        DBCItem dbcItem = new DBCItem(getConnection());
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            int itemId = dbcItem.findItemByName(name);
            System.out.println("1");
            statement.setInt(1, itemId);
            System.out.println("2");
            try (ResultSet resultSet = statement.executeQuery()) {
                MedicalKit MedicalKit = findMedicalKitById(itemId);
                return MedicalKit;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MedicalKit findMedicalKitById(int itemId){
        String sql = "SELECT * FROM medicalKit WHERE itemId = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, itemId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    if(findItemById(itemId) != null) {
                        Item item = findItemById(itemId);
                        MedicalKit MedicalKit1 = new MedicalKit(itemId, item.getName(), item.getWeight(),
                                item.getWidth(), item.getLength(), item.getDurability(),
                                resultSet.getInt("healingPower"));
                        return MedicalKit1;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    
    public void updateMedicalKit(int id, int healingPower){
        String sql = "UPDATE medicalKit SET healinPower = ? WHERE itemId = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, healingPower);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMedicalKit(MedicalKit medicalKit){
        String itemSql = "UPDATE item SET name = ?, weight = ?, width = ?, length = ?, durability = ? WHERE itemId = ?";
        String medicalKitSql = "UPDATE medicalKit SET healingPower = ? WHERE itemId = ?";
        try (PreparedStatement itemStatement = getConnection().prepareStatement(itemSql);
             PreparedStatement medicalKitStatement = getConnection().prepareStatement(medicalKitSql)) {
            itemStatement.setString(1, medicalKit.getName());
            itemStatement.setDouble(2, medicalKit.getWeight());
            itemStatement.setInt(3, medicalKit.getWidth());
            itemStatement.setInt(4, medicalKit.getLength());
            itemStatement.setInt(5, medicalKit.getDurability());
            itemStatement.setInt(6, medicalKit.getItemId());
            itemStatement.executeUpdate();

            medicalKitStatement.setInt(1, medicalKit.getHealingPower());
            medicalKitStatement.setInt(2, medicalKit.getItemId());
            medicalKitStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMedicalKit(int id) throws SQLException {
        String sql = "DELETE FROM medicalKit WHERE itemId = ?";
        int itemId = findMedicalKitById(id).getItemId();
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Набор первой помощи удален!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBCItem dbcItem = new DBCItem(getConnection());
        dbcItem.deleteItem(itemId);
    }

    @Override
    public List getAll(){
        String sql = "SELECT * FROM medicalKit";
        List<MedicalKit> medicalKits = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int itemId = resultSet.getInt("itemId");
                Item item = findItemById(itemId);
                MedicalKit medicalKit = new MedicalKit(itemId, item.getName(), item.getWeight(),
                        item.getWidth(), item.getLength(), item.getDurability(),
                        resultSet.getInt("healingPower"));
                medicalKits.add(medicalKit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medicalKits;
    }
}
