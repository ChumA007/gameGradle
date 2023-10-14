package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;
    private int id;  // Поле id для идентификации игрока

    //@JsonCreator используется в конструкторе, который принимает необходимые свойства json в качестве параметров
    //@JsonProperty используются для указания соответствия между свойствами json и параметрами конструктора
    @JsonCreator
    public Inventory(@JsonProperty("id") int id) {
        this.id = id;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public int getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public String toString(){
        String str = "Inventory: ";
        for (int i = 0; i < this.items.size(); i++){
            if (this.items.get(i) != null) {
                str += "    " + this.items.get(i).toString();
            }
        }

        return str +="\n";
    }
}
