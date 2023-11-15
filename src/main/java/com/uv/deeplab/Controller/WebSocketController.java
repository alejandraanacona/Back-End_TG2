package com.uv.deeplab.Controller;


import com.uv.deeplab.Service.SupportFunctions.SubscriptorRos;
import com.uv.deeplab.config.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;


@Controller

public class WebSocketController {
    @Autowired
	private SimpMessagingTemplate messageTemplate;
    @Autowired
    private SubscriptorRos nodoEscucha;
    @MessageMapping("/receive")

    @SendTo("/topic/messages")
    public String greeting(String mensaje) throws Exception {
        Console.logInfo("entra","Al menos aquì");
        //nodoEscucha.nodeSubscriptor();
        //messageTemplate.convertAndSend("/topic/messages", mensaje);
        System.out.println("Lo que llega y se envía:" +mensaje );

        return(mensaje);

    }
    //@MessageMapping("/receive")
    public void saludar2(String message){
        System.out.println("Lo que llega :" + message);
    }

}


