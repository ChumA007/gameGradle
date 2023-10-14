package org.example.items;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

//@JsonTypeInfo используется для включения информации о типе в сериализованный json
// помогает различать подклассы для десериализации
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @Type(value = Item.class, name = "item"),
        @Type(value = Weapon.class, name = "damage"),
        @Type(value = MedicalKit.class, name = "healingPower")
})
public class Item {
    private String name;
    private double weight;
    private int width;
    private int length;
    private int durability;

    public Item(String name, double weight, int width, int length, int durability) {
        this.name = name;
        this.weight = weight;
        this.width = width;
        this.length = length;
        this.durability = durability;
    }

    public Item(){}

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getDurability() {
        return durability;
    }

    protected void setDurability(int durability){
        this.durability = durability;
    }

    public String toString(){
        return this.name + " (weight: " + this.weight + ", width: " + this.width + ", length: "
                + this.length + " durability: " + this.durability;
    }
}
