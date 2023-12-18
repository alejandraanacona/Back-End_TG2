package com.uv.deeplab.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uv.deeplab.Dto.JoystickData;
import com.uv.deeplab.Dto.LidarDataSend;
import com.uv.deeplab.Service.SupportFunctions.SubscriptorsRos;
import com.uv.deeplab.config.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;


@Controller

public class WebSocketController {

    @Autowired
    SubscriptorsRos subscriptorRos;
   /* @Autowired
    private SubscriptorsRos nodoEscucha;*/
    @MessageMapping("/receive")
    @SendTo("/topic/messages")
    public void joyControl(String mensaje) throws Exception {
        Console.logInfo("entra", "Al menos aquì");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JoystickData joystickData = objectMapper.readValue(mensaje, JoystickData.class);
            subscriptorRos.ServoPublish(joystickData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //subscriptorRos.nodeSubscriptor();

        System.out.println("Lo que llega y se envía de la camera: " +mensaje );
        //return (mensaje);
    }


    //SE ENCUENTRA COMENTADO PORQUE ES PARA LA CAMARA
    /*public String camera(String mensaje) throws Exception {
        Console.logInfo("entra","Al menos aquì");
        //nodoEscucha.nodeSubscriptor();
        //messageTemplate.convertAndSend("/topic/messages", mensaje);
        System.out.println("Lo que llega y se envía de la camera: " +mensaje );

        return(mensaje);

    }*/
    @MessageMapping("/receive2")
    @SendTo("/topic/messages2")
    public LidarDataSend lidar(LidarDataSend mensaje) throws Exception {
        Console.logInfo("entra","Al menos aquì");
        //nodoEscucha.nodeSubscriptor();
        //messageTemplate.convertAndSend("/topic/messages", mensaje);
        System.out.println("Lo que llega y se envía del lidar: " +mensaje );

        return(mensaje);

    }
    public void saludar2(String message){
        System.out.println("Lo que llega :" + message);
    }

}


