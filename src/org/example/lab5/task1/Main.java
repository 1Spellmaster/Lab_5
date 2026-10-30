package org.example.lab5.task1;

import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введіть шлях та ім'я текстового файлу (UTF-8): ");
            String fileName = scanner.nextLine().trim();
            if (fileName.isEmpty()) {
                throw new IllegalArgumentException("Шлях до файлу не може бути порожнім.");
            }
            String line = new LineFileReader().findLineWithMostWords(Path.of(fileName));
            System.out.println(line == null ? "Файл порожній." : "Рядок з найбільшою кількістю слів: " + line);
        } catch (IOException | IllegalArgumentException | NoSuchElementException e) {
            System.out.println("Не вдалося прочитати файл: " + e.getMessage());
        }
    }
}
