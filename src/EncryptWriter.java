import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

public class EncryptWriter extends FilterWriter {
    private final char key;

    public EncryptWriter(Writer writer, char key) {
        super(writer);
        this.key = key;
    }

    @Override
    public void write(int c) throws IOException {
        // Перетворення на char залишає код у діапазоні 0..65535.
        out.write((char) (c + key));
    }

    @Override
    public void write(char[] buffer, int offset, int length) throws IOException {
        Objects.checkFromIndexSize(offset, length, buffer.length);
        for (int i = offset; i < offset + length; i++) {
            write(buffer[i]);
        }
    }

    @Override
    public void write(String text, int offset, int length) throws IOException {
        Objects.checkFromIndexSize(offset, length, text.length());
        for (int i = offset; i < offset + length; i++) {
            write(text.charAt(i));
        }
    }
}
