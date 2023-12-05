package org.example.items;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Weapon extends Item {


    private int damage;

    public Weapon(int itemId, String name, double weight, int widthInCells, int lengthInCells, int durability, int damage) {
        super(itemId, name, weight, widthInCells, lengthInCells, durability);
        this.damage = damage;
    }

    public Weapon() {}

    public int getDamage() {
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
        return super.toString() + ", damage: " + this.damage + ")\n";
    }


    public void setDamage(int damage) {
        this.damage = damage;
    }
}