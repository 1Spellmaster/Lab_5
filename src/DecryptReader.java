import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

public class DecryptReader extends FilterReader {
    private final char key;

    public DecryptReader(Reader reader, char key) {
        super(reader);
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int c = in.read();
        return c == -1 ? -1 : (char) (c - key);
    }

    @Override
    public int read(char[] buffer, int offset, int length) throws IOException {
        Objects.checkFromIndexSize(offset, length, buffer.length);
        if (length == 0) {
            return 0;
        }
        int count = in.read(buffer, offset, length);
        for (int i = offset; i < offset + count; i++) {
            buffer[i] = (char) (buffer[i] - key);
        }
        return count;
    }
}
