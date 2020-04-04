package ru.geekbrains.java2.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HistoryWriter {
    private static File file;


    public HistoryWriter(String login) {
        file = new File(String.format("history_%s.txt", login));
    }

    public synchronized void writeHistory(String line) {
        System.out.println(line);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(line);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл");
            e.printStackTrace();
        }
    }

}
