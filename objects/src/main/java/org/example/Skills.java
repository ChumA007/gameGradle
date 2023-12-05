package org.example;

public class Skills {
    private int id;
    private int strength = 0;
    private int endurance = 0;
    private int perception = 0;

    public Skills(int id, int strength, int endurance, int perception) {
        this.id = id;
        this.strength = strength;
        this.endurance = endurance;
        this.perception = perception;
    }

    public Skills (){
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getPerception() {
        return perception;
    }

    public void setPerception(int perception) {
        this.perception = perception;
    }

    public void increaseStrength(int value) {
        strength += value;
    }

    public void increaseEndurance(int value) {
        endurance += value;
    }

    public void increasePerception(int value) {
        perception += value;
    }

    public String toString() {
        return "Skills: " + "Strength: " + ", Endurance: " + endurance + ", Perception: " + perception + "\n";
    }
}
