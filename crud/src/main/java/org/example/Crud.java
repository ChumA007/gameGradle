package org.example;

import org.example.DataBaseClasses.*;
import org.example.DataBaseClasses.Item.DBCItem;
import org.example.DataBaseClasses.Item.DBCMedicalKit;
import org.example.DataBaseClasses.Item.DBCWeapon;
import org.example.items.Item;
import org.example.items.MedicalKit;
import org.example.items.Weapon;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Crud {

    private DBCPlayers dbcPlayers;
    private DBCQuests dbcQuests;
    private DBCLevel dbcLevel;
    private DBCHealth dbcHealth;
    private DBCInventory dbcInventory;
    private DBCBalance dbcBalance;
    private DBCItem dbcItem;
    private DBCMedicalKit dbcMedicalKit;
    private DBCWeapon dbcWeapon;
    private DBCSkills dbcSkills;
    public Crud(Connection connection) {
        this.dbcPlayers = new DBCPlayers(connection);
        this.dbcQuests = new DBCQuests(connection);
        this.dbcLevel = new DBCLevel(connection);
        this.dbcHealth = new DBCHealth(connection);
        this.dbcInventory = new DBCInventory(connection);
        this.dbcBalance = new DBCBalance(connection);
        this.dbcItem = new DBCItem(connection);
        this.dbcMedicalKit = new DBCMedicalKit(connection);
        this.dbcWeapon = new DBCWeapon(connection);
        this.dbcSkills = new DBCSkills(connection);
    }
    public void addPlayer(String name) throws SQLException {
        dbcPlayers.addPlayer(name);
    }

    public void addItem(List<Item> list) throws SQLException {
        dbcItem.addItem(list);
    }

    public void addPlayer(Player player) throws SQLException {
        dbcPlayers.addPlayer(player);
    }

    public void addWeapon(Weapon weapon) throws SQLException {
        dbcWeapon.addWeapon(weapon);
    }

    public void addQuests(Player player) throws SQLException {
        dbcQuests.addQuests(player);
    }

    public void addMedicalKit(MedicalKit medicalKit) throws SQLException {
        dbcMedicalKit.addMedicalKit(medicalKit);
    }

    public void addSkill(Player player){
        dbcSkills.addSkills(player);
    }

    public List<Player> findPlayer(String name){
        return dbcPlayers.findPlayerByName(name);
    }

    public Player findPlayer(int id) throws SQLException {
        return  dbcPlayers.findPlayerById(id);
    }

    public Weapon findWeapon(int itemId){
        return dbcWeapon.findWeaponById(itemId);
    }

    public Weapon findWeaponByName(String name) {return  dbcWeapon.findWeaponByName(name);}

    public MedicalKit findMedicalKit(int itemId){
        return dbcMedicalKit.findMedicalKitById(itemId);
    }

    public MedicalKit findMedicalKitByName(String name) {return  dbcMedicalKit.findMedicalKitByName(name);}

    public void updatePlayer(int id, String name, int level, int experience, int strength, int endurance, int perception) throws SQLException {
        dbcPlayers.updatePlayer(id, name);
        dbcSkills.updateSkills(id, strength, endurance, perception);
        dbcLevel.updateLevel(id, level, experience);
    }

    public void updatePlayer(int id, String name){
        dbcPlayers.updatePlayer(id, name);
    }

    public void updateWeapon(int id, int durability){
        dbcWeapon.updateWeapon(id, durability);
    }

    public void updateWeapon(Weapon weapon){
        dbcWeapon.updateWeapon(weapon);
    }


    public void updateMedicalKit(int id, int healingPower){
        dbcMedicalKit.updateMedicalKit(id, healingPower);
    }

    public void updateMedicalKit(MedicalKit medicalKit){
        dbcMedicalKit.updateMedicalKit(medicalKit);
    }

    public void updateSkill(int id, int strength, int endurance, int perception) throws SQLException {
        dbcSkills.updateSkills(id, strength, endurance, perception);
    }

    public void deletePlayer(int id) throws SQLException {
        dbcPlayers.deletePlayer(id);
    }

    public void deleteWeapon(int id) throws SQLException {
        dbcWeapon.deleteWeapon(id);
    }

    public void deleteMedicalKit(int id) throws SQLException {
        dbcMedicalKit.deleteMedicalKit(id);
    }

    public void deleteInventory(int id){
        dbcInventory.deleteInventoryById(id);
    }

    public List<Player> getAllPlayer() throws SQLException {
        return dbcPlayers.getAll();
    }

    public List<Weapon> getAllWeapon() throws SQLException {
        return dbcWeapon.getAll();
    }

    public List<MedicalKit> getAllMedicalKit() throws SQLException {
        return dbcMedicalKit.getAll();
    }

    public List<Quest> getAllSkills() throws SQLException {
        return dbcQuests.getAll();
    }
}
