package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Money {
    private int id;
    private double amount;
    private String name;

    //@JsonCreator используется в конструкторе, который принимает необходимые свойства json в качестве параметров
    //@JsonProperty используются для указания соответствия между свойствами json и параметрами конструктора
    @JsonCreator
    public Money(@JsonProperty("id") int id, @JsonProperty("amount") double amount, @JsonProperty("name") String name) {
        this.id = id;
        this.amount = amount;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}