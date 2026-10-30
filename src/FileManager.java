import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    public static BufferedReader openTextFile(String fileName) throws IOException {
        return Files.newBufferedReader(getPath(fileName), StandardCharsets.UTF_8);
    }

    public static void saveString(String fileName, String text) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream(getPath(fileName).toFile()))) {
            output.writeObject(text);
        }
    }

    public static String loadString(String fileName)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream input = new ObjectInputStream(
                new FileInputStream(getPath(fileName).toFile()))) {
            Object data = input.readObject();
            if (!(data instanceof String)) {
                throw new IOException("Файл повинен містити зашифрований рядок.");
            }
            return (String) data;
        }
    }

    private static Path getPath(String fileName) {
        if (fileName.isBlank()) {
            throw new IllegalArgumentException("Шлях до файлу не може бути порожнім.");
        }
        return Path.of(fileName);
    }
}
