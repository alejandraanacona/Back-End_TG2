package com.uv.deeplab.Service.SupportFunctions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uv.deeplab.Dto.*;
import com.uv.deeplab.config.Console;

import edu.wpi.rail.jrosbridge.Ros;
import edu.wpi.rail.jrosbridge.Topic;
import edu.wpi.rail.jrosbridge.callback.TopicCallback;
import edu.wpi.rail.jrosbridge.messages.Message;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service

//public class SubscriptorsRos implements Runnable {
public class SubscriptorsRos {


    /* public static void main(String[] args) throws InterruptedException {
         SubscriptorRos.nodeSubscriptor();
     }*/
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public void nodeSubscriptor() {
        executorService.submit(() -> subscribeToTopic("/ctrl_pkg/servo_msg", "deepracer_interfaces_pkg/ServoCtrlMsg", "/app/receive"));
        //executorService.submit(() -> subscribeToTopic("localhost", "/scan", "sensor_msgs/LaserScan", "/app/receive2"));
    }
   /*public void nodeSubscriptor() {


       // Connect to the ROS server
       Ros ros = new Ros("localhost");
       ros.connect();

       Thread thread1 = new Thread(() ->subscribeToTopic(ros, "/topic", "/app/receive"));
       Thread thread2 = new Thread(() ->subscribeToTopic(ros, "/topic2", "/app/receive2"));

       thread1.start();
       thread2.start();

   }*/
    public Ros ros;
    public void connectToRos() {
        ros = new Ros("localhost");
        ros.connect();
        Console.logInfo("ROSBRIDGE SERVER", "CONNECTED");
    }

    private void subscribeToTopic( String topicName, String Type, String senderPath) {

        Topic echoBack = new Topic(ros, topicName, Type);

        //----------------------------------------------LÓGICA PARA SUSCRIBIRSE-----------------------------------
        echoBack.subscribe(new TopicCallback() {
            @Override
            public void handleMessage(Message message) {
               try{
                   if(topicName=="/scan") {
                       LidarDataSend dataSend = processDataLidar(message.toString());
                       Sender(dataSend, senderPath);
                       Console.logInfo("SUSCRITO AL TOPICO",topicName);
                       System.out.println("Desde ROS :" + message.toString());
                   }else if (topicName.equals("/topic2")) {
                       // Manejar el mensaje como String
                       String msgString = extractDataFromJsonCamera(message.toString());
                       SenderCam(msgString, senderPath);
                       Console.logInfo("SUSCRITO AL TOPICO",topicName);

                   } else {
                       System.out.println("AQUI IRIA EL OTRO");
                   }

                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //processDataLidar(message.toString());
                //extractDataFromJson(message.toString());
                System.out.println("Desde ROS :" + message.toString());

            }
        });
        //---------------------------------------LÓGICA PARA PUBLICAR-----------------------------------------------------


        /*Message toSend = new Message("{\"angle\": -1.0 , \"throttle\": 0.5}");
        System.out.println("MENSAJE QUE SE ENVIA:"+ toSend);
        echoBack.publish(toSend);*/


        // Disconnect from ROS
        //ros.disconnect();

    }

    public void publishToTopic(String topicName, String messageType, String jsonData) {
        if (ros != null && ros.isConnected()) {
            Topic topic = new Topic(ros, topicName, messageType);
            Message message = new Message(jsonData);
            topic.publish(message);
        } else {
            Console.logError("ROS Connection", "Not connected to ROS. Cannot publish to topic.");
        }
    }


    public void ServoPublish (JoystickData mensaje) throws JsonProcessingException {
        Console.logInfo("Si llea al servopublish", "con esto" + mensaje);
        DataControl control = DataControlProcess(mensaje);
        //Console.logInfo("ESTA ES LA DATA DE ENVIAR", ":"+ control);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(control); // data es una instancia de DataControl
        System.out.println("ESTA ES LA DATA DE ENVIAR: " + jsonData);

        publishToTopic("/ctrl_pkg/servo_msg","deepracer_interfaces_pkg/ServoCtrlMsg",jsonData);
    }


        private LidarData extractDataFromJson(String jsonMessage) {

        List<Double> rangeList = new ArrayList<>();
        List<Double> intensityList = new ArrayList<>();

        LidarData lidarData = new LidarData();

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonMessage);

            // Obtener los arrays de rangos e intensidades
            lidarData.setAngleMin(jsonNode.get("angle_min").asDouble());
            lidarData.setAngleMax(jsonNode.get("angle_max").asDouble());
            lidarData.setAngleIncre(jsonNode.get("angle_increment").asDouble());
            lidarData.setTimeIncre(jsonNode.get("time_increment").asDouble());
            lidarData.setScanTime(jsonNode.get("scan_time").asDouble());
            lidarData.setRangeMax(jsonNode.get("range_max").asDouble());
            JsonNode rangesNode = jsonNode.get("ranges");
            JsonNode intensitiesNode = jsonNode.get("intensities");
            //System.out.println("Valor de Rango: " + rangesNode);

            // Obtener el valor asociado con la clave "data"

            if (rangesNode != null && intensitiesNode != null) {

                for (JsonNode range : rangesNode) {
                    //double rangeValue = range.asDouble();
                    rangeList.add(range.asDouble());
                    lidarData.setRanges(rangeList);
                    // Realizar operaciones matemáticas con el valor de rango
                    //System.out.println("Valor de Rango: " + rangeList);
                }

                for (JsonNode intensity : intensitiesNode) {
                    //double intensityValue = intensity.asDouble();
                    intensityList.add(intensity.asDouble());
                    lidarData.setIntensities(intensityList);
                    // Realizar operaciones matemáticas con el valor de intensidad
                    //System.out.println("Valor de Intensidad: " + intensityValue);
                }
            } else {
                System.out.println("El mensaje no contiene los arrays esperados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("Valor de Rango: " + lidarData.getRanges());
        //System.out.println("Valor de intensides: " + lidarData.getIntensities());
        //System.out.println("Valor de angulomin: " + lidarData.getAngleMin());


        return (lidarData);
    }

    private LidarDataSend processDataLidar(String mensaje){
        LidarDataSend lidarDataSend = new LidarDataSend();
        LidarData lidarData= extractDataFromJson(mensaje);

        //filtrar mediciones inválidas
        List<Integer> validRangesIndices = getValidRangesIndices(lidarData.getRanges());
        List<Double> validRanges = getValidRanges(lidarData.getRanges(), validRangesIndices);

        //Calcular los angulos
        List<Double> angles= calculateAngles(validRanges.size(),lidarData.getAngleIncre(),lidarData.getAngleMin());

        //Calcular coordenadas x,y

        List<Double> x= calculateX(validRanges,angles);
        List<Double> y = calculateY(validRanges,angles);

        lidarDataSend.setRangeX(x);
        lidarDataSend.setRangeY(y);
        lidarDataSend.setIntensities(lidarData.getIntensities());

        System.out.println("Valor de rangosx: " + lidarDataSend.getRangeX());
        System.out.println("Valor de rangosy: " + lidarDataSend.getRangeY());
        System.out.println("Valor de intensities: " + lidarDataSend.getIntensities());



        return (lidarDataSend);
    }

    private static List<Integer> getValidRangesIndices(List<Double> ranges) {
        List<Integer> validRangesIndices = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < ranges.size(); i++) {
            if (!Double.isNaN(ranges.get(i))) {
                validRangesIndices.add(i);
                count++;
            }
        }

        return validRangesIndices;
    }

    private static List<Double> getValidRanges(List<Double> ranges, List<Integer> validRangesIndices) {
        List<Double> validRanges = new ArrayList<>();

        for (Integer index : validRangesIndices) {
            validRanges.add(ranges.get(index));
        }

        return validRanges;
    }

    private static List<Double> calculateAngles(int length, double angleIncrement, double angleMin) {
        List<Double> angles = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            angles.add(i * angleIncrement + angleMin);
        }

        return angles;
    }
    private static List<Double> calculateX(List<Double> ranges, List<Double> angles) {
        List<Double> x = new ArrayList<>();

        for (int i = 0; i < ranges.size(); i++) {
            x.add(ranges.get(i) * Math.cos(angles.get(i)));
        }

        return x;
    }

    private static List<Double> calculateY(List<Double> ranges, List<Double> angles) {
        List<Double> y = new ArrayList<>();

        for (int i = 0; i < ranges.size(); i++) {
            y.add(ranges.get(i) * Math.sin(angles.get(i)));
        }

        return y;
    }
    public void Sender(LidarDataSend dataSend, String destination) throws ExecutionException, InterruptedException {
        Console.logInfo("ENTRO A HACER", "LA CONEXIÒN");
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ClientOneSessionHandler clientOneSessionHandler = new ClientOneSessionHandler();
        ListenableFuture<StompSession> sessionAsync = stompClient.connect("ws://localhost:5430/ws", clientOneSessionHandler);
        StompSession session = sessionAsync.get();
        session.subscribe("/topic", clientOneSessionHandler);

        LidarDataSend updateMsg = modifyMessage(dataSend);
        session.send(destination, updateMsg);
        Console.logInfo("SE ENVIÒ", "EL HOLA");
        //Thread.sleep();
    }


    private LidarDataSend modifyMessage(LidarDataSend lidarDataSend) {
        return lidarDataSend;
    }

    class ClientOneSessionHandler extends StompSessionHandlerAdapter {
        //@Override
        //public Type getPayLoadType(StompHeaders headers)
        //{return HolaMundo.class;
        //}
        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
        }

    }

    //LOGICA PARA CAMARA
    public void SenderCam(String mensaje, String destination) throws ExecutionException, InterruptedException {
        Console.logInfo("ENTRO A HACER", "LA CONEXIÒN");
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ClientOneSessionHandler clientOneSessionHandler = new ClientOneSessionHandler();
        ListenableFuture<StompSession> sessionAsync = stompClient.connect("ws://localhost:5430/ws", clientOneSessionHandler);
        StompSession session = sessionAsync.get();
        session.subscribe("/topic", clientOneSessionHandler);

        //LidarDataSend updateMsg = modifyMessage(dataSend);
        String updateMsg = mensaje;
        session.send(destination, updateMsg);
        Console.logInfo("SE ENVIÒ", "EL HOLA");
        //Thread.sleep();
    }

    public String extractDataFromJsonCamera(String mensaje){
        String mensajeReturn=mensaje;
        return (mensajeReturn);
    }

    public DataControl DataControlProcess (JoystickData mensaje){
        Console.logInfo("ENTRA A CONTROLDATA","PERO NO HACE NADA");
        DataControl control = new DataControl();
        Double sumaCuadrados =0.0;
        Double Magnitud=0.0;
        Double signo = 0.0;
        Double angle= 0.0;
        Integer angleNorma= 270; //Angulo de desplazamiento para la funciòn seno

        Double VecX = mensaje.getVector().getX();
        Double VecY = mensaje.getVector().getY();
        Double Angle = mensaje.getAngle().getDegree();

        signo = Math.signum(VecY);
        sumaCuadrados += Math.pow(VecX,2)+ Math.pow(VecY,2);
        Magnitud = Math.sqrt(sumaCuadrados)*signo;
        control.setThrottle(Magnitud);

        //ANGULOS A NORMALIZAR angle y 270

        Angle = Math.sin(Math.toRadians(Angle)-Math.toRadians(angleNorma));
        control.setAngle(Angle);

        return (control);
    }

}


