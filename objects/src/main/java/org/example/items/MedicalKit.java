package org.example.items;

public class MedicalKit extends Item {
    private int healingPower;

    public MedicalKit(String name, double weight, int widthInCells, int lengthInCells, int durability, int healingPower) {
        super(name, weight, widthInCells, lengthInCells, durability);
        this.healingPower = healingPower;
    }

    public MedicalKit(){}

    public int getHealingPower() {
        return healingPower;
    }

    public int use() {
        if (getDurability() <= 0) {
            System.out.println("MedicalKit is depleted and cannot be used.");
            return 0;
        }

        int actualHealing = Math.min(healingPower, getDurability());
        // Уменьшаем прочность на величину восстановленного здоровья
        setDurability(getDurability() - actualHealing);
        System.out.println("MedicalKit used. Restored " + actualHealing + " health.");
        return actualHealing;
    }

    public void setDurability(int durability) {
        super.setDurability(durability);
    }

    public String toString(){
        return super.toString() + ", healing power: " + this.healingPower + ")\n";
    }
}
