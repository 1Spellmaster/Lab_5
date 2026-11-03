package org.example.lab5.task3;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class ShiftWriter extends FilterWriter {
    private final int shift;
    private int pendingHighSurrogate = -1;

    public ShiftWriter(Writer writer, int shift) {
        super(writer);
        this.shift = shift;
    }

    @Override
    public void write(int c) throws IOException {
        char character = (char) c;
        if (pendingHighSurrogate != -1) {
            if (!Character.isLowSurrogate(character)) {
                throw new IOException("Некоректний символ у вхідному файлі.");
            }
            int codePoint = Character.toCodePoint((char) pendingHighSurrogate, character);
            pendingHighSurrogate = -1;
            out.write(Character.toChars(CharShift.shift(codePoint, shift)));
        } else if (Character.isHighSurrogate(character)) {
            pendingHighSurrogate = character;
        } else if (Character.isLowSurrogate(character)) {
            throw new IOException("Некоректний символ у вхідному файлі.");
        } else {
            out.write(Character.toChars(CharShift.shift(character, shift)));
        }
    }

    @Override
    public void write(char[] buffer, int offset, int length) throws IOException {
        if (offset < 0 || length < 0 || offset > buffer.length - length) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = offset; i < offset + length; i++) {
            write(buffer[i]);
        }
    }

    @Override
    public void write(String text, int offset, int length) throws IOException {
        if (offset < 0 || length < 0 || offset > text.length() - length) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = offset; i < offset + length; i++) {
            write(text.charAt(i));
        }
    }

    @Override
    public void close() throws IOException {
        boolean incomplete = pendingHighSurrogate != -1;
        pendingHighSurrogate = -1;
        super.close();
        if (incomplete) {
            throw new IOException("Некоректний символ у вхідному файлі.");
        }
    }
}
