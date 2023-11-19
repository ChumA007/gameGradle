package org.example;

import org.example.DataBaseClasses.DBCPlayers;
import org.example.items.MedicalKit;
import org.example.items.Weapon;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


//реализовал консольку небольшую для проверки работы бд
public class Console {

    private static final Scanner scanner = new Scanner(System.in);

    private static Crud crud;

    static void start(){
        System.out.println("Add: \n1. New player" +
                "\n2. New item\n3. Remake\n4. Find\n5. Delete\n6. Get all players" +
                "\n7. Exit");
        int i = scanner.nextInt();
        while (i != 7) {
            switch (i) {
                case 1:
                    addPlayer();
                case 2:
                    System.out.println("1. Weapon\n2. Medical kit\n3. Back");
                    int i1 = scanner.nextInt();
                    switch (i1) {
                        case 1:
                            addWeapon();
                            System.out.println("Weapon created");
                        case 2:
                            addMedicalKit();
                            System.out.println("Medical kit created");
                        case 3:
                            start();
                    }
                case 3:
                    update();
                case 4:
                    find();
                case 5:
                    delete();
                case 6:
                    getAllPlayers();
                case 7:
                    System.exit(0);
            }
        }
    }

    private static void getAllPlayers(){
        try {
            crud = new Crud(Connect.connector());
            List<Player> list = crud.getAllPlayer();
            for(Player pl : list){
                System.out.println(pl.toString());
            }
            start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getAllWeapons(){
        try {
            crud = new Crud(Connect.connector());
            List<Weapon> list = crud.getAllWeapon();
            for(Weapon w : list){
                System.out.println(w.toString());
            }
            start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getAllMedicalKit(){
        try {
            crud = new Crud(Connect.connector());
            List<MedicalKit> mK = crud.getAllMedicalKit();
            for(MedicalKit mk : mK){
                System.out.println(mk.toString());
            }
            start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addWeapon() {
        try{
            crud = new Crud(Connect.connector());
            Random random = new Random();
            int wId = random.nextInt(900) + 100;
            System.out.println("Enter the name: ");
            String name = scanner.next();
            System.out.println("Weight (kg): ");
            double weight = scanner.nextDouble();
            System.out.println("Wigth and length (mm): ");
            int width = scanner.nextInt();
            int length = scanner.nextInt();
            System.out.println("Durability (%): ");
            int durability = scanner.nextInt();
            System.out.println("Damage: ");
            int damage = scanner.nextInt();
            Weapon weapon = new Weapon(wId, name, weight, width, length,
                    durability, damage);
            crud.addWeapon(weapon);
            start();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void addMedicalKit() {
        try{
            crud = new Crud(Connect.connector());
            Random random = new Random();
            int mkId = random.nextInt(900) + 100;
            System.out.println("Enter the name: ");
            String name = scanner.next();
            System.out.println("Weight (kg): ");
            double weight = scanner.nextInt();
            System.out.println("Wigth and length (mm): ");
            int width = scanner.nextInt();
            int length = scanner.nextInt();
            System.out.println("Durability (%): ");
            int durability = scanner.nextInt();
            System.out.println("Healing power: ");
            int healingPower = scanner.nextInt();
            MedicalKit medicalKit = new MedicalKit(mkId, name, weight, width, length,
                    durability, healingPower);
            crud.addMedicalKit(medicalKit);
            start();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void addPlayer(){
        try{
            crud = new Crud(Connect.connector());
            System.out.println("Enter the nickname: ");
            String name = scanner.next();
            crud.addPlayer(name);
            System.out.println("Player created");
            start();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void find(){
        System.out.println("Find: \n1. Player \n2. Weapon \n3. Medical kit \n4. Back");
        int i = scanner.nextInt();
        switch (i){
            case 1:
                findPlayer();
            case 2:
                findWeapon();
            case 3:
                findMedicalKit();
            case 4:
                start();
        }
    }

    private static void findPlayer(){
        try{
            crud = new Crud(Connect.connector());
            System.out.println("Nickname: ");
            String name = scanner.next();
            crud.findPlayer(name);
            start();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void findWeapon(){
        try{
            crud = new Crud(Connect.connector());
            System.out.println("Enter the id: ");
            int itemId = scanner.nextInt();
            if(crud.findWeapon(itemId) != null){
                System.out.println(crud.findWeapon(itemId).toString());
            } else {
                System.out.println("Weapon not found");
            }
            start();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void findMedicalKit(){
        try{
            crud = new Crud(Connect.connector());
            System.out.println("Enter the id: ");
            int itemId = scanner.nextInt();
            if(crud.findMedicalKit(itemId) != null){
                System.out.println(crud.findMedicalKit(itemId).toString());
            } else {
                System.out.println("Medical kit not found");
            }
            start();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void update(){
        System.out.println("Remake: \n1. Player \n2. Weapon \n3. Medical kit \n4. Back");
        int i = scanner.nextInt();
        switch (i){
            case 1:
                updatePlayer();
            case 2:
                rebalanceWeapon();
            case 3:
                rebalanceMedicalKit();
            case 4:
                start();
        }
    }

    private static void rebalanceWeapon(){
        try{
            crud = new Crud(Connect.connector());
            System.out.println("Enter id: ");
            int id = scanner.nextInt();
            System.out.println("Enter new name: ");
            String name = scanner.next();
            System.out.println("Enter: weight (kg), width (mm), length (mm), durability (%), damage: ");
            double weight = scanner.nextInt();
            int width = scanner.nextInt();
            int length = scanner.nextInt();
            int durability = scanner.nextInt();
            int damage = scanner.nextInt();
            crud.updateWeapon(new Weapon(id, name, weight, width, length, durability, damage));
            start();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void rebalanceMedicalKit(){
        try{
            crud = new Crud(Connect.connector());
            System.out.println("Enter id: ");
            int id = scanner.nextInt();
            System.out.println("Enter new name: ");
            String name = scanner.next();
            System.out.println("Enter: weight (kg), width (mm), length (mm), durability (%), healing power: ");
            double weight = scanner.nextInt();
            int width = scanner.nextInt();
            int length = scanner.nextInt();
            int durability = scanner.nextInt();
            int healingPower = scanner.nextInt();
            crud.updateMedicalKit(new MedicalKit(id, name, weight, width, length, durability, healingPower));
            start();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void updatePlayer(){
        try{
            crud = new Crud(Connect.connector());
            System.out.println("Enter id: ");
            int id = scanner.nextInt();
            System.out.println("Enter new nickname: ");
            String nickname = scanner.next();
            System.out.println("Enter level, experience: ");
            int level = scanner.nextInt();
            int experience = scanner.nextInt();
            System.out.println("Enter skills - strength, endurance, perception:");
            int strength = scanner.nextInt();
            int endurance = scanner.nextInt();
            int perception = scanner.nextInt();
            crud.updatePlayer(id, nickname, level, experience, strength, endurance, perception);
            start();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    private static void delete(){
        System.out.println("Delete: \n1. Player \n2. Weapon \n3. Medical kit \n4. Back");
        int i = scanner.nextInt();
        switch (i){
            case 1:
                deletePlayer();
            case 2:
                deleteWeapon();
            case 3:
                deleteMedicalKit();
            case 4:
                start();
        }
    }

    private static void deletePlayer(){
        try{
            crud = new Crud(Connect.connector());
            System.out.println("Enter id: ");
            int id = scanner.nextInt();
            crud.deletePlayer(id);
            System.out.println("Player deleted");
            start();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void deleteWeapon(){
        try{
            crud = new Crud(Connect.connector());
            System.out.println("Enter id: ");
            int id = scanner.nextInt();
            crud.deleteWeapon(id);
            System.out.println("Weapon deleted");
            start();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void deleteMedicalKit(){
        try{
            crud = new Crud(Connect.connector());
            System.out.println("Enter id: ");
            int id = scanner.nextInt();
            crud.deleteMedicalKit(id);
            System.out.println("Medical kit deleted");
            start();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
