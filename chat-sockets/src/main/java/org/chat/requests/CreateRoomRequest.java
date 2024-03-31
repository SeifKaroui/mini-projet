package org.chat.requests;

import java.io.Serializable;

public class CreateRoomRequest implements Serializable {
    final private String roomName;

    public CreateRoomRequest(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    @Override
    public String toString() {
        return "Create Room request with name: " + this.roomName ;
    }
}
