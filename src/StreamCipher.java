import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class StreamCipher {
    public static String encrypt(String text, char key) throws IOException {
        StringWriter result = new StringWriter();
        try (EncryptWriter writer = new EncryptWriter(result, key)) {
            writer.write(text);
        }
        return result.toString();
    }

    public static String decrypt(String text, char key) throws IOException {
        StringWriter result = new StringWriter();
        try (DecryptReader reader = new DecryptReader(new StringReader(text), key)) {
            char[] buffer = new char[1024];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                result.write(buffer, 0, count);
            }
        }
        return result.toString();
    }
}
