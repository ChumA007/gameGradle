package org.example;

import org.example.items.MedicalKit;
import org.example.items.Weapon;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
    private static final int MIN_LEVEL = 1;
    private static final int MAX_LEVEL = 50;
    private Random random;

    public Generator() {
        this.random = new Random();
    }

    public List<Player> generatePlayers(int count) {
        List<Player> players = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < count; i++) {
            Player player = new Player(id, generateRandomNickname(), generateRandomSkill(), generateRandomQuests(),
                    generateRandomInventory(id), generateRandomLevel(),
                    generateRandomHealth(), generateRandomBalance());
            players.add(player);
            id++;
        }

        return players;
    }

    private String generateRandomNickname() {
        List<String> nicknames = new ArrayList<>(Arrays.asList("John", "Mike", "Emily", "Sophia", "David", "Oliver"));
        int index = random.nextInt(nicknames.size());
        return nicknames.get(index);
    }

    //skills - умения: сила, выносливость и восприятие
    //соответственно, по мере игры они прокачиваются
    private Skills generateRandomSkill() {
        int strength = random.nextInt(100) + 1;
        int endurance = random.nextInt(100) + 1;
        int perception = random.nextInt(100) + 1;
        return new Skills(strength, endurance, perception);
    }

    //есть 2 типа квестов: оперативные и перманентные
    private Quests generateRandomQuests() {
        Quests quests = new Quests();
        int count = random.nextInt(4);
        for (int i = 0; i < count; i++){
            String name = "Quest " + random.nextInt(100);
            String desc = "Description of Quest " + random.nextInt(100);
            int deadline;
            //при помощи конструкции do...while генерируется число, которое является количеством дней,
            //до конца оперативного (временного) квеста
            do {
                deadline = random.nextInt(12) - 1;
            } while (deadline == 0);
            quests.addQuest(new Quest(name, desc, deadline));
        }

        return quests;
    }

    //метод генерирует рандомный уровень и текущий опыт
    //(опыт от 1 до 999, так как для перехода на след. уровень нужно ровно 1000 опыта)
    private Level generateRandomLevel() {
        int level = random.nextInt(MAX_LEVEL - MIN_LEVEL + 1) + MIN_LEVEL;
        int experience = random.nextInt(999) + 1;
        return new Level(level, experience);
    }

    private Health generateRandomHealth() {
        int health = random.nextInt(100) + 1;
        int maxHealth = random.nextInt(51) + 100;
        return new Health(health, maxHealth);
    }

    //генерируем рандомное значение валюты от 0 до 100000 или до 1000
    //в классе Balance хранится список, в котором хранятся объекты Money
    //для удобства добавления новых валют, если потребуется
    private Balance generateRandomBalance() {
        int rubles = random.nextInt(100001);
        int dollars = random.nextInt(1001);
        int euros = random.nextInt(1001);

        Money rublesMoney = new Money(1, rubles, "Rubles");
        Money dollarsMoney = new Money(2, dollars, "Dollars");
        Money eurosMoney = new Money(3, euros, "Euros");

        Balance balance = new Balance();
        balance.addMoney(rublesMoney);
        balance.addMoney(dollarsMoney);
        balance.addMoney(eurosMoney);

        return balance;
    }

    //передаем id игрока, чтобы по инвентарю можно было идентифицировать игрока
    private Inventory generateRandomInventory(int id){
        Weapon[] weapons = {new Weapon("AK-101", 2.3, 2, 4, 100, 20),
                new Weapon("HK416A5", 1.25, 2, 4, 100, 30)};
        MedicalKit[] medicalKits = {new MedicalKit("Grizzly",1.6,2,2,1800,175),
        new MedicalKit("AI-2", 0.5, 1, 1, 100, 50)};
        Inventory inventory = new Inventory(id);
        int count = random.nextInt(4);
        for (int i = 0; i < count; i++){
            inventory.addItem(weapons[random.nextInt(2)]);
            inventory.addItem(medicalKits[random.nextInt(2)]);
        }

        return inventory;
    }
}