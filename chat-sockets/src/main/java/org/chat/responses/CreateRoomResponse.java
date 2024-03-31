package org.chat.responses;

import java.io.Serializable;

public class CreateRoomResponse implements Serializable {
    final private int code;

    public CreateRoomResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
