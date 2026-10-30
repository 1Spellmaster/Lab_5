package org.example.lab5.task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineFileReader {
    private static final Pattern WORD = Pattern.compile("\\S+", Pattern.UNICODE_CHARACTER_CLASS);

    public String findLineWithMostWords(Path path) throws IOException {
        String longestLine = null;
        int mostWords = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int words = 0;
                Matcher matcher = WORD.matcher(line);
                while (matcher.find()) {
                    words++;
                }
                if (longestLine == null || words > mostWords) {
                    longestLine = line;
                    mostWords = words;
                }
            }
        }

        return longestLine;
    }
}
