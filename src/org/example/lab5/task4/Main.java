package org.example.lab5.task4;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введіть URL сторінки (Enter — https://example.com): ");
            String url = scanner.nextLine().trim();
            if (url.isEmpty()) {
                url = "https://example.com";
            }
            Map<String, Integer> counts = new TagCounter().count(url);
            printTable("Теги сторінки " + url + " за алфавітом:",
                    counts, Map.Entry.comparingByKey());
            System.out.println();
            printTable("Теги за зростанням частоти:", counts,
                    Comparator.comparingInt((Map.Entry<String, Integer> entry) -> entry.getValue())
                            .thenComparing(Map.Entry::getKey));
        } catch (IOException | IllegalArgumentException | NoSuchElementException e) {
            System.out.println("Не вдалося прочитати сторінку: " + e.getMessage());
        }
    }

    private static void printTable(String title, Map<String, Integer> counts,
                                   Comparator<Map.Entry<String, Integer>> order) {
        System.out.println(title);
        System.out.printf("%-12s | %10s%n", "Тег", "Кількість");
        System.out.println("-------------+-----------");
        counts.entrySet().stream()
                .sorted(order)
                .forEach(entry -> System.out.printf("%-12s | %10d%n", entry.getKey(), entry.getValue()));
    }
}
