package org.example.DataBaseClasses;

import org.example.DataBaseClasses.Item.DBCItem;
import org.example.DataBaseClasses.Item.DBCMedicalKit;
import org.example.DataBaseClasses.Item.DBCWeapon;
import org.example.Inventory;
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

public class DBCInventory extends DBC{
    public DBCInventory(Connection connection) {
        super(connection);
    }

    public void addInventory(Player player){
        List<Item> inventory = player.getInventory().getItems();
        String sql = "INSERT INTO inventory (id, itemId) VALUES (?,?)";


        for(Item i: inventory){
            try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
                statement.setInt(1, player.getId());
                statement.setInt(2, i.getItemId());
                statement.executeUpdate();
                System.out.println("Inventory created");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Inventory findInventoryByPlayerId(int id){
        String sql = "SELECT * FROM inventory WHERE id = ?";
        //DBCItem dbcItem = new DBCItem(getConnection());
        DBCMedicalKit dbcMedicalKit = new DBCMedicalKit(getConnection());
        DBCWeapon dbcWeapon = new DBCWeapon(getConnection());
        List<Item> inventory = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int itemId = resultSet.getInt("itemId");
                MedicalKit medicalKit = dbcMedicalKit.findMedicalKitById(itemId);
                Weapon weapon = dbcWeapon.findWeaponById(itemId);
                inventory.add(new Item(medicalKit == null ? weapon : medicalKit));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Inventory(inventory);
    }

    public void updateInventoryById(Player player){
        String sql = "UPDATE inventory SET itemId = ? WHERE id = ?";
        List<Item> inventory = player.getInventory().getItems();
        for(Item i: inventory){
            try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
                statement.setInt(1, i.getItemId());
                statement.setInt(2, player.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void deleteInventoryById(int id){
        String sql = "DELETE FROM inventory WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Nнвентарь удален");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Inventory> getAll() throws SQLException {
        return null;
    }
}
