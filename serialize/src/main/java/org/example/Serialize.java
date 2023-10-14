package org.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.List;

public class Serialize{
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Serialize <outputFile>");
            System.exit(1);
        }

        String filename = args[0];
        Generator generator = new Generator();
        //доработал код, теперь есть возможность создать 1000000 игроков (появилась поэлементная запись в файл)
        List<Player> players = generator.generatePlayers(1000000);
        serialize(players, filename);
    }

    public static void serialize(List<Player> players, String file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        File output = new File(file);

        if (checkFileHasRecords(file)){
            clearFile(file);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            for (Player p : players) { //перебирает всех игроков в списке
                String json = mapper.writeValueAsString(p); //преобразует объект Player в его json-представление
                writer.write(json); //записывает строку в файл
                writer.newLine(); //добавляет новую строку после каждой записи, чтобы игроки выводились в столбец
            }
        }
    }


    private static boolean checkFileHasRecords(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            reader.close();
            return line != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void clearFile(String filename) {
        try {
            FileWriter writer = new FileWriter(filename, false);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}