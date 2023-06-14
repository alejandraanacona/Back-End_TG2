package com.uv.deeplab.Service;


import com.uv.deeplab.config.Console;

public class LoginMesage {
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

    public LoginMesage(String message, Boolean status) {
        Console.logInfo("Si cuajo", "Entró a la función login mesage");
        this.message = message;
        this.status = status;
    }
}
