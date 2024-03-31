package org.chat.requests;

import org.chat.Message;

import java.io.Serializable;

public class SendMessageRequest implements Serializable {
    final private String roomName;
    final private Message message;

    public SendMessageRequest(String roomName, Message message) {
        this.roomName = roomName;
        this.message = message;
    }

    public String getRoomName() {
        return roomName;
    }

    public Message getMessage() {
        return message;
    }
}
