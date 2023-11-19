package org.example;

public class Level {
    private int id;
    private int level;
    private int experience;
    private static final int EXPERIENCE_PER_LEVEL = 1000; // Количество опыта для поднятия уровня

    public Level(int id, int initialLevel, int initialExperience) {
        this.id = id;
        this.level = initialLevel;
        this.experience = initialExperience;
        updateLevel();
    }

    public Level(){}

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public void gainExperience(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Experience amount must be non-negative.");
        }
        experience += amount;
        updateLevel();
    }

    private void updateLevel() {
        int newLevel = experience / EXPERIENCE_PER_LEVEL;
        if (newLevel > level) {
            level = newLevel;
            System.out.println("Congratulations! You have reached level " + level);
        }
    }
}