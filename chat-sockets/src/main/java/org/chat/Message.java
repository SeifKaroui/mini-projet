package org.chat;

import java.io.Serializable;

public class Message implements Serializable {
    final private String author;
    final private String content;

    public Message(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return author + ":\n" + content;
    }
}
