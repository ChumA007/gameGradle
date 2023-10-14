package org.example;

public class Player {

    private int id;
    private String nickname;
    private Skills skills;
    private Quests quests;
    private Level level;
    private Inventory inventory;
    private Health health;
    private Balance balance;

    public Player(int id, String nickname, Skills skills, Quests quests, Inventory inventory, Level level, Health health, Balance balance){
        this.id = id;
        this.nickname = nickname;
        this.skills = skills;
        this.quests = quests;
        this.level = level;
        this.inventory = inventory;
        this.health = health;
        this.balance = balance;
    }

    public Player() {
    }

    public String toString(){
        return "\nPlayer id: " + this.id + "\nNickname: " + this.nickname +
                "\nQuests:\n" + this.quests.toString() + this.skills.toString() + this.inventory.toString() +
                this.health.toString() + this.balance.toString();
    }
}