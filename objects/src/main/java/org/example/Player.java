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

    public Player(int id, String nickname){
        this.id = id;
        this.nickname = nickname;
    }

    public Player() {
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public Quests getQuests() {
        return quests;
    }

    public void setQuests(Quests quests) {
        this.quests = quests;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public String toString(){
        return "\nPlayer id: " + this.id + "\nNickname: " + this.nickname +
                "\nQuests:\n" + this.quests.toString() + this.skills.toString() + this.inventory.toString() +
                this.health.toString() + this.balance.toString();
    }
}