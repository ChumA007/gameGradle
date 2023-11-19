package org.example;

import java.util.ArrayList;
import java.util.List;

public class Balance {

    private int id;
    private List<Money> moneyList;

    public Balance(int id) {
        this.id = id;
        this.moneyList = new ArrayList<>();
    }

    public void addMoney(Money money) {
        moneyList.add(money);
    }

    public void spendMoney(int moneyId, double amount) {
        for (Money money : moneyList) {
            if (money.getId() == moneyId) {
                if (money.getAmount() >= amount) {
                    money.setAmount(money.getAmount() - amount);
                } else {
                    System.out.println("Not enough money with ID " + moneyId + " to make the purchase.");
                }
                return;
            }
        }
        System.out.println("Money with ID " + moneyId + " not found.");
    }

    public String toString() {
        StringBuilder result = new StringBuilder("Balance:\n");
        for (Money money : moneyList) {
            result.append(String.format("      %s: %.2f %s\n", money.getName(), money.getAmount(), money.getName()));
        }
        return result.toString();
    }

    public List<Money> getMoneyList() {
        return moneyList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMoneyList(List<Money> moneyList) {
        this.moneyList = moneyList;
    }
}
