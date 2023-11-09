package com.uv.deeplab.Controller;

import com.uv.deeplab.Service.SupportFunctions.SubscriptorRos;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller

public class WebSocketController {

    public SubscriptorRos subscriptorRos;
    @MessageMapping("/app")
    @SendTo("/topic/messages")
    public String handleMessage(String message) {
                return "Recibiste: " + message;
    }
}
