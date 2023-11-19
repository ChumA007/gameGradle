package org.example.DataBaseClasses;

import org.example.Balance;
import org.example.Money;
import org.example.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBCBalance extends DBC{
    public DBCBalance(Connection connection) {
        super(connection);
    }

    public void addBalance(Player player){
        List<Money> money = player.getBalance().getMoneyList();
        String sql = "INSERT INTO balance (id, name, amount) VALUES (?,?,?)";
        for(Money m: money){
            try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
                statement.setInt(1, player.getId());
                statement.setString(2, m.getName());
                statement.setDouble(3, m.getAmount());
                statement.executeUpdate();
                System.out.println("Добавление успешно!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateBalance(Balance balance){
        for (Money m: balance.getMoneyList()){
            updateMoney(m);
        }
    }

    public void updateMoney(Money money){
        String sql = "UPDATE balance SET value = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setDouble(1, money.getAmount());
            statement.setInt(2, money.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteBalance(Balance balance){
        for (Money m: balance.getMoneyList()){
            deleteMoney(m.getId());
        }
    }

    public void deleteMoney(int id){
        String sql = "DELETE FROM balance WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Деньги с баланса удалены!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Balance> getAll() throws SQLException {
        return null;
    }
}