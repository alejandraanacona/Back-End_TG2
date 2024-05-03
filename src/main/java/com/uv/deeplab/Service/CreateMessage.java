package com.uv.deeplab.Service;

import com.uv.deeplab.config.Console;

public class CreateMessage {

    String message;
    Boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public CreateMessage(String message, Boolean status) {
        Console.logInfo("Si cuajo", "Entró a la función create mesagge");
        this.message = message;
        this.status = status;
    }
}
