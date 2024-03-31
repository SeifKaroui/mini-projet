package org.chat.requests;

import java.io.Serializable;

public class GetRoomMessagesRequest implements Serializable {
    final private String roomName;

    public GetRoomMessagesRequest(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }
}
