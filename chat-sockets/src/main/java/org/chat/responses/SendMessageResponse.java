package org.chat.responses;

import java.io.Serializable;

public class SendMessageResponse implements Serializable {
    final private int code;

    public SendMessageResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
