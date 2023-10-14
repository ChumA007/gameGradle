package org.example;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Health {
    private int currentHealth;
    private int maxHealth;

    public Health(int currentHealth, int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
    }

    public Health(){}

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        if (currentHealth < 0) {
            this.currentHealth = 0;
        } else if (currentHealth > maxHealth) {
            this.currentHealth = maxHealth;
        } else {
            this.currentHealth = currentHealth;
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void heal(int amount) {
        if (currentHealth + amount <= maxHealth) {
            currentHealth += amount;
        } else {
            currentHealth = maxHealth;
        }
    }

    public void takeDamage(int amount) {
        if (currentHealth - amount >= 0) {
            currentHealth -= amount;
        } else {
            currentHealth = 0;
        }
    }

    @JsonIgnore
    public boolean isAlive() {
        return currentHealth > 0;
    }

    public String toString(){
        return "Current health: " + this.currentHealth + " Max health: " + this.maxHealth + "\n";
    }
}
