package org.example;

public class Skills {
    private int strength;
    private int endurance;
    private int perception;

    public Skills(int strength, int endurance, int perception) {
        this.strength = strength;
        this.endurance = endurance;
        this.perception = perception;
    }

    public Skills (){}

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
        return "Skills: " + "Strength: " + strength + ", Endurance: " + endurance + ", Perception: " + perception + "\n";
    }
}
