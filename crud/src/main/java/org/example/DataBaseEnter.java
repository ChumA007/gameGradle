package org.example;


import org.example.items.Item;
import org.example.items.MedicalKit;
import org.example.items.Weapon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseEnter {

    private static Crud crud;

    public static void main(String[] args) throws SQLException {

        Statement statement = Connect.connector().createStatement();
        String truncateQuery = "TRUNCATE TABLE balance, health, inventory, item, level, medicalKit, players, quests, skills, weapon";
        statement.execute(truncateQuery);
        System.out.println("Database cleared.");
        generateItems();
        generateMedicalKit();
        generateWeapons();
        generatePlayers();
    }

    private static void generateItems() throws SQLException{
        crud = new Crud(Connect.connector());
        List<Item> items = DataBaseGenerator.generateItems();
        crud.addItem(items);
    }

    public static void generateMedicalKit() throws SQLException {
        crud = new Crud(Connect.connector());
        List<MedicalKit> medicalKits = DataBaseGenerator.generateMedicalKits();
        for(MedicalKit medicalKit : medicalKits){
            crud.addMedicalKit(medicalKit);
        }
    }

    public static void generateWeapons() throws SQLException {
        crud = new Crud(Connect.connector());
        List<Weapon> weapons = DataBaseGenerator.generateWeapons();
        for(Weapon weapon: weapons){
            crud.addWeapon(weapon);
        }
    }

    public static void generatePlayers() throws SQLException {
        crud = new Crud(Connect.connector());
        List<Player> players = DataBaseGenerator.generatePlayers(10);
        for(Player player: players){
            crud.addPlayer(player);
            crud.addSkill(player);
            crud.addQuests(player);
        }
    }
}
