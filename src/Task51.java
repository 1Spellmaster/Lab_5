import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class Task51 {
    private static final Pattern WORD = Pattern.compile("\\S+", Pattern.UNICODE_CHARACTER_CLASS);

    public static String findLineWithMostWords(String fileName) throws IOException {
        String result = null;
        int maxWords = -1;

        try (BufferedReader reader = FileManager.openTextFile(fileName)) {
            String line;
            while ((line = reader.readLine()) != null) {
                int words = countWords(line);
                if (words > maxWords) {
                    maxWords = words;
                    result = line;
                }
            }
        }
        return result;
    }

    public static int countWords(String line) {
        int count = 0;
        var matcher = WORD.matcher(line);
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
