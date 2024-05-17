package com.uv.deeplab.Service;

import com.uv.deeplab.config.Console;

public class HorarioMessage {

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

    public HorarioMessage(String message, Boolean status){
        Console.logInfo("Entra", "a la función login mesage");
        this.message = message;
        this.status = status;
    }
}
