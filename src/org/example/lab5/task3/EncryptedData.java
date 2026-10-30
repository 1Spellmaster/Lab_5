package org.example.lab5.task3;

import java.io.Serializable;

public class EncryptedData implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String text;

    public EncryptedData(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
