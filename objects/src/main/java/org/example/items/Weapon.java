package org.example.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;

public class Weapon extends Item {

    private int damage;

    public Weapon(String name, double weight, int widthInCells, int lengthInCells, int durability, int damage) {
        super(name, weight, widthInCells, lengthInCells, durability);
        this.damage = damage;
    }

    public Weapon() {}

    public double getDamage() {
        return damage;
    }

    public void use() {
        if (getDurability() > 0) {
            setDurability(getDurability() - 1);
            System.out.println("Weapon used. Durability decreased.");
        } else {
            System.out.println("Weapon is broken and cannot be used.");
        }
    }

    // Метод для проверки, сломано ли оружие
    @JsonIgnore
    public boolean isBroken() {
        return getDurability() <= 0;
    }

    public String toString(){
        return super.toString() + ", healing power: " + this.damage + ")\n";
    }
}