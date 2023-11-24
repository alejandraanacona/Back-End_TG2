package com.uv.deeplab.Service.SupportFunctions;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uv.deeplab.Dto.LidarData;
import com.uv.deeplab.Dto.LidarDataSend;
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
        executorService.submit(() -> subscribeToTopic("localhost", "/topic2", "std_msgs/String", "/app/receive"));
        executorService.submit(() -> subscribeToTopic("localhost", "/scan", "sensor_msgs/LaserScan", "/app/receive2"));
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

    private void subscribeToTopic(String rosServer, String topicName, String Type, String senderPath) {
        Ros ros = new Ros(rosServer);
        ros.connect();

        Topic echoBack = new Topic(ros, topicName, Type);

        echoBack.subscribe(new TopicCallback() {
            @Override
            public void handleMessage(Message message) {
               try{
                   LidarDataSend dataSend = processDataLidar(message.toString());
                    Sender(dataSend, senderPath);
                    System.out.println("Desde ROS :" + message.toString());
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                processDataLidar(message.toString());
                //extractDataFromJson(message.toString());
                System.out.println("Desde ROS :" + message.toString());

            }
        });
        /*echoBack.subscribe(new TopicCallback() {

            @Override
            public void handleMessage(Message message) {
                try {
                    String msgString=extractDataFromJson(message.toString());
                    Sender(msgString, "/app/receive");
                    System.out.println("Desde ROS :" + message.toString());
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
       Topic echoBack2 = new Topic(ros, "/topic2", "std_msgs/String");

        echoBack2.subscribe(new TopicCallback() {
            @Override
            public void handleMessage(Message message) {
                try {
                    String msgString=extractDataFromJson(message.toString());
                    Sender(msgString,"/app/receive2");
                    System.out.println("Desde ROS :" + message.toString());
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });*/
        // Keep the application alive to listen to messages
        /*try {
            Thread.sleep(200000); // Sleep for 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        // Disconnect from ROS
        //ros.disconnect();

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


}


