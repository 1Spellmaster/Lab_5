package org.example.lab5.task3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CipherFileService {
    public void encrypt(Path source, Path destination, char key) throws IOException {
        checkPaths(source, destination);
        String encrypted = writeShifted(new BufferedReader(
                new FileReader(source.toFile(), StandardCharsets.UTF_8)), key);

        try (ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream(destination.toFile()))) {
            output.writeObject(new EncryptedData(encrypted));
        }
    }

    public void decrypt(Path source, Path destination, char key) throws IOException {
        checkPaths(source, destination);
        EncryptedData data;
        try (ObjectInputStream input = new ObjectInputStream(
                new FileInputStream(source.toFile()))) {
            Object object = input.readObject();
            if (!(object instanceof EncryptedData)) {
                throw new IOException("Файл не містить зашифрованих даних цієї програми.");
            }
            data = (EncryptedData) object;
            if (data.getText() == null) {
                throw new IOException("У файлі відсутній зашифрований текст.");
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Не вдалося відновити зашифровані дані.", e);
        }

        String decrypted = readShifted(new StringReader(data.getText()), -key);
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(destination.toFile(), StandardCharsets.UTF_8))) {
            writer.write(decrypted);
        }
    }

    private void checkPaths(Path source, Path destination) throws IOException {
        if (source.toAbsolutePath().normalize().equals(destination.toAbsolutePath().normalize())
                || (Files.exists(destination) && Files.isSameFile(source, destination))) {
            throw new IOException("Вхідний і вихідний файли повинні бути різними.");
        }
    }

    private String writeShifted(Reader source, int shift) throws IOException {
        StringWriter result = new StringWriter();
        try (Reader reader = source; ShiftWriter writer = new ShiftWriter(result, shift)) {
            char[] buffer = new char[4096];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, count);
            }
        }
        return result.toString();
    }

    private String readShifted(Reader source, int shift) throws IOException {
        StringBuilder result = new StringBuilder();
        try (ShiftReader reader = new ShiftReader(source, shift)) {
            char[] buffer = new char[4096];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                result.append(buffer, 0, count);
            }
        }
        return result.toString();
    }
}
