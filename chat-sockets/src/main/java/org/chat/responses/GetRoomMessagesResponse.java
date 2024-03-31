package org.chat.responses;

import org.chat.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class GetRoomMessagesResponse implements Serializable {
    final private int code;
    final private ArrayList<Message> messages;

    public GetRoomMessagesResponse(int code, ArrayList<Message> messages) {
        this.code = code;
        this.messages = messages;
    }

    public int getCode() {
        return code;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
