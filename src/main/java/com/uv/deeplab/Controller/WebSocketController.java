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

    private final  SimpMessagingTemplate template;
    @Autowired
    SubscriptorsRos subscriptorRos;

    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    /* @Autowired
     private SubscriptorsRos nodoEscucha;*/
    @MessageMapping("/receive")
    //@SendTo("/topic/messages")
    public void joyControl(String mensaje) throws Exception {
        Console.logInfo("entra", "Al controller");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JoystickData joystickData = objectMapper.readValue(mensaje, JoystickData.class);
            Console.logInfo("entra", "enfo joystick" +joystickData);
            /*if (joystickData.getAngle2()==0.0 && joystickData.getThrottle()==0.0){
                subscriptorRos.ServoPublishStop(joystickData);
            }else {

            }*/
                subscriptorRos.ServoPublish(joystickData);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //subscriptorRos.nodeSubscriptor();

        //System.out.println("Lo que llega y se envía de la camera: " +mensaje );
        //return (mensaje);
    }


    @MessageMapping("/receive2")
    @SendTo("/topic/messages2")
    //SE ENCUENTRA COMENTADO PORQUE ES PARA LA CAMARA
    public String camera(String mensaje) throws Exception {
        Console.logInfo("entra","Al menos aquì");
        //nodoEscucha.nodeSubscriptor();
        //messageTemplate.convertAndSend("/topic/messages", mensaje);
        System.out.println("Lo que llega y se envía de la camera: " +mensaje );

        return(mensaje);
    }

    @MessageMapping("/receive3")
    @SendTo("/topic/messages3")
    public LidarDataSend lidar(LidarDataSend mensaje) throws Exception {

        Console.logInfo("entra","Al menos aquì");
        //nodoEscucha.nodeSubscriptor();
        //messageTemplate.convertAndSend("/topic/messages", mensaje);
        //System.out.println("Lo que llega y se envía del lidar: " +mensaje );

        return(mensaje);
    }

    public void sendOutput(String message) {
        this.template.convertAndSend("/topic/output", message);
    }

}


