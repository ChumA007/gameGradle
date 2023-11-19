package org.example;

import org.example.items.Item;
import org.example.items.MedicalKit;
import org.example.items.Weapon;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DataBaseGenerator {
    private static final int MIN_LEVEL = 1;
    private static final int MAX_LEVEL = 50;

    private static Weapon[] weapons = {new Weapon(111, "AK-101", 2.3, 2, 4, 100, 20),
            new Weapon(222, "HK416A5", 1.25, 2, 4, 100, 30)};
    private static MedicalKit[] medicalKits = {new MedicalKit(333, "Grizzly",1.6,2,2,1800,175),
            new MedicalKit(444, "AI-2", 0.5, 1, 1, 100, 50)};

    private static Crud crud;

    static {
        try {
            crud = new Crud(Connect.connector());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DataBaseGenerator() {

    }


    public static List<Player> generatePlayers(int count) {
        List<Player> players = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < count; i++) {
            Player player = new Player(id, generateRandomNickname(), generateRandomSkill(id), generateRandomQuests(id),
                    generateRandomInventory(id), generateRandomLevel(id),
                    generateRandomHealth(id), generateRandomBalance(id));
            players.add(player);
            id+=2;
        }

        return players;
    }

    private static String generateRandomNickname() {
        List<String> nicknames = new ArrayList<>(Arrays.asList("John", "Mike", "Emily", "Sophia", "David", "Oliver"));
        Random random = new Random();
        int index = random.nextInt(nicknames.size());

        return nicknames.get(index);
    }

    //skills - умения: сила, выносливость и восприятие
    //соответственно, по мере игры они прокачиваются
    private static Skills generateRandomSkill(int id) {
        Random random = new Random();
        int strength = random.nextInt(100) + 1;
        int endurance = random.nextInt(100) + 1;
        int perception = random.nextInt(100) + 1;
        return new Skills(id, strength, endurance, perception);
    }

    //есть 2 типа квестов: оперативные и перманентные
    private static Quests generateRandomQuests(int id) {
        Random random = new Random();
        Quests quests = new Quests(id);
        int count = random.nextInt(5)+1;
        for (int i = 0; i < count; i++){
            String name = "Quest " + random.nextInt(100);
            String desc = "Description of Quest " + random.nextInt(100);
            int deadline;
            //при помощи конструкции do...while генерируется число, которое является количеством дней,
            //до конца оперативного (временного) квеста
            do {
                deadline = random.nextInt(12) - 1;
            } while (deadline == 0);
            quests.addQuest(new Quest(id, name, desc, deadline, false));
        }

        return quests;
    }

    //метод генерирует рандомный уровень и текущий опыт
    //(опыт от 1 до 999, так как для перехода на след. уровень нужно ровно 1000 опыта)
    private static Level generateRandomLevel(int id) {
        Random random = new Random();
        int level = random.nextInt(MAX_LEVEL - MIN_LEVEL + 1) + MIN_LEVEL;
        int experience = random.nextInt(999) + 1;
        return new Level(id, level, experience);
    }

    private static Health generateRandomHealth(int id) {
        Random random = new Random();
        int health = random.nextInt(100) + 1;
        int maxHealth = random.nextInt(51) + 100;
        return new Health(id, health, maxHealth);
    }

    //генерируем рандомное значение валюты от 0 до 100000 или до 1000
    //в классе Balance хранится список, в котором хранятся объекты Money
    //для удобства добавления новых валют, если потребуется
    private static Balance generateRandomBalance(int id) {
        Random random = new Random();
        int rubles = random.nextInt(100001);
        int dollars = random.nextInt(1001);
        int euros = random.nextInt(1001);

        Money rublesMoney = new Money(id, rubles, "Rubles");
        Money dollarsMoney = new Money(id, dollars, "Dollars");
        Money eurosMoney = new Money(id, euros, "Euros");

        Balance balance = new Balance(id);
        balance.addMoney(rublesMoney);
        balance.addMoney(dollarsMoney);
        balance.addMoney(eurosMoney);

        return balance;
    }

    //передаем id игрока, чтобы по инвентарю можно было идентифицировать игрока
    private static Inventory generateRandomInventory(int id){
        Random random = new Random();

        Inventory inventory = new Inventory(id);
        int count = random.nextInt(3);
        for (int i = 0; i < count; i++){
            inventory.addItem(weapons[random.nextInt(2)]);
            inventory.addItem(medicalKits[random.nextInt(2)]);
        }

        return inventory;
    }

    public static List<MedicalKit> generateMedicalKits(){
        List<MedicalKit> mk = new ArrayList<>();
        for (MedicalKit m : medicalKits){
            mk.add(m);
        }
        return mk;
    }

    public static List<Weapon> generateWeapons(){
        List<Weapon> weapons1 = new ArrayList<>();
        for (Weapon w : weapons){
            weapons1.add(w);
        }
        return weapons1;
    }

    public static List<Item> generateItems(){
        List<Item> items = new ArrayList<>();
        int itemId = 1;
        for (MedicalKit i : medicalKits){
            Item item = new Item(itemId, i.getName(), i.getWeight(), i.getWidth(), i.getLength(), i.getDurability());
            items.add(item);
            itemId++;
        }
        for (Weapon i : weapons){
            Item item = new Item(itemId, i.getName(), i.getWeight(), i.getWidth(), i.getLength(), i.getDurability());
            items.add(item);
            itemId++;
        }
        return items;
    }
}
