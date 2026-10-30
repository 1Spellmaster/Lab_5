package org.example.lab5.task3;

import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("1 — зашифрувати, 2 — розшифрувати: ");
            String operation = scanner.nextLine().trim();
            if (!operation.equals("1") && !operation.equals("2")) {
                throw new IllegalArgumentException("Невідома команда.");
            }

            System.out.print("Введіть ключовий символ: ");
            String key = scanner.nextLine();
            if (key.length() != 1 || Character.isSurrogate(key.charAt(0))) {
                throw new IllegalArgumentException("Ключ має складатися з одного символу.");
            }

            System.out.print("Введіть шлях та ім'я вхідного файлу: ");
            String sourceName = scanner.nextLine().trim();
            System.out.print("Введіть шлях та ім'я вихідного файлу: ");
            String destinationName = scanner.nextLine().trim();
            if (sourceName.isEmpty() || destinationName.isEmpty()) {
                throw new IllegalArgumentException("Шлях до файлу не може бути порожнім.");
            }

            CipherFileService files = new CipherFileService();
            Path source = Path.of(sourceName);
            Path destination = Path.of(destinationName);
            if (operation.equals("1")) {
                files.encrypt(source, destination, key.charAt(0));
            } else {
                files.decrypt(source, destination, key.charAt(0));
            }
            System.out.println("Результат записано у файл: " + destination);
        } catch (IOException | IllegalArgumentException | NoSuchElementException e) {
            System.out.println("Помилка роботи з файлом: " + e.getMessage());
        }
    }
}
