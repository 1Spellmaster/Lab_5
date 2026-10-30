import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n1 - Завдання 5.1: рядок із найбільшою кількістю слів");
                System.out.println("3 - Завдання 5.3: шифрування та дешифрування");
                System.out.println("4 - Завдання 5.4: частота HTML-тегів");
                System.out.println("0 - Вихід");
                System.out.print("Ваш вибір: ");

                try {
                    int choice = Integer.parseInt(scanner.nextLine().trim());
                    switch (choice) {
                        case 0:
                            return;
                        case 1:
                            runTask51(scanner);
                            break;
                        case 3:
                            runTask53(scanner);
                            break;
                        case 4:
                            runTask54(scanner);
                            break;
                        default:
                            throw new IllegalArgumentException("Немає такого пункту меню.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Некоректне введення: " + e.getMessage());
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Помилка роботи з даними: " + e.getMessage());
                } catch (NoSuchElementException e) {
                    return;
                }
            }
        }
    }

    private static void runTask51(Scanner scanner) throws IOException {
        System.out.print("Введіть повний шлях та ім'я текстового файлу (UTF-8): ");
        String line = Task51.findLineWithMostWords(scanner.nextLine().trim());
        if (line == null) {
            System.out.println("Файл порожній.");
        } else {
            System.out.println("Рядок із найбільшою кількістю слів:");
            System.out.println(line);
            System.out.println("Кількість слів: " + Task51.countWords(line));
        }
    }

    private static void runTask53(Scanner scanner)
            throws IOException, ClassNotFoundException {
        System.out.print("1 - Зашифрувати, 2 - Дешифрувати: ");
        int action = Integer.parseInt(scanner.nextLine().trim());
        if (action != 1 && action != 2) {
            throw new IllegalArgumentException("Оберіть 1 або 2.");
        }

        System.out.print("Введіть один ключовий символ: ");
        String key = scanner.nextLine();
        if (key.length() != 1) {
            throw new IllegalArgumentException("Ключ повинен містити один символ Java char.");
        }

        if (action == 1) {
            System.out.print("Введіть текст для шифрування: ");
            String encrypted = StreamCipher.encrypt(scanner.nextLine(), key.charAt(0));
            System.out.print("Введіть повний шлях та ім'я файлу для збереження: ");
            FileManager.saveString(scanner.nextLine().trim(), encrypted);
            System.out.println("Зашифрований текст збережено.");
        } else {
            System.out.print("Введіть повний шлях та ім'я зашифрованого файлу: ");
            String encrypted = FileManager.loadString(scanner.nextLine().trim());
            System.out.println("Розшифрований текст:");
            System.out.println(StreamCipher.decrypt(encrypted, key.charAt(0)));
        }
    }

    private static void runTask54(Scanner scanner) throws IOException {
        System.out.print("Введіть URL сторінки (Enter - https://example.com): ");
        String url = scanner.nextLine().trim();
        if (url.isEmpty()) {
            url = "https://example.com";
        }
        Task54.printCounts(Task54.countTags(url));
    }
}
