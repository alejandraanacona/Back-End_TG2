package com.uv.deeplab.Service.SupportFunctions;

import com.uv.deeplab.Service.HolaMundo;
import com.uv.deeplab.config.Console;
import edu.wpi.rail.jrosbridge.Ros;
import edu.wpi.rail.jrosbridge.Topic;
import edu.wpi.rail.jrosbridge.callback.TopicCallback;
import edu.wpi.rail.jrosbridge.messages.Message;
import org.eclipse.jetty.websocket.api.extensions.Frame;
import org.glassfish.tyrus.websockets.uri.internal.UriComponent;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
@Service
public class SubscriptorRos {

   /* public static void main(String[] args) throws InterruptedException {
        SubscriptorRos.nodeSubscriptor();
    }*/

        public void nodeSubscriptor() {


            // Connect to the ROS server
            Ros ros = new Ros("localhost");
            ros.connect();

            Topic echoBack = new Topic(ros, "/topic", "std_msgs/String");
            //final AtomicReference<String> messageString = new AtomicReference<>();

            echoBack.subscribe(new TopicCallback() {

                //private SimpMessagingTemplate messageTemplate;
                @Override
                public void handleMessage(Message message) {
                    try {
                        Sender(message.toString());
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Desde ROS :" + message);
                }
            });
            // Keep the application alive to listen to messages
            try {
                Thread.sleep(200000); // Sleep for 10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Disconnect from ROS
            //ros.disconnect();

        }


    public void Sender(String msg) throws ExecutionException, InterruptedException {
        Console.logInfo("ENTRO A HACER","LA CONEXIÒN");
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        //StompHeaders headers = new StompHeaders();
        //headers.add("Authorization", "Bearer tu_token");

        ClientOneSessionHandler clientOneSessionHandler = new ClientOneSessionHandler();
        ListenableFuture<StompSession> sessionAsync = stompClient.connect("ws://localhost:5430/ws", clientOneSessionHandler);
        StompSession session = sessionAsync.get();
        session.subscribe("/topic", clientOneSessionHandler);
        while (true) {
            session.send("/app/receive", msg);
            Console.logInfo("SE ENVIÒ","EL HOLA");
            Thread.sleep(2000);
        }
    }

    class ClientOneSessionHandler extends StompSessionHandlerAdapter {
            //@Override
            //public Type getPayLoadType(StompHeaders headers)
            //{return HolaMundo.class;
            //}
            @Override
            public void handleFrame(StompHeaders headers, Object payload){
            }



        // URL del servidor WebSocket (ajusta según tu configuración)
       /* String webSocketUrl = "ws://localhost:/ws";

        // Endpoint al que queremos enviar mensajes
        String destination = "/app/receive";

        // Crear el cliente WebSocket STOMP
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        // Conectar al servidor WebSocket

        StompHeaders connectedHeaders;
        session.subscribe("/topic/messages", connectedHeaders );
        StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {

                // Enviar el mensaje al endpoint deseado
                session.send(destination, msg.getBytes());
            }
        };

        try {
            // Conectar al servidor WebSocket y ejecutar la lógica después de la conexión
            ListenableFuture<StompSession> SessionAsyn = stompClient.connect(webSocketUrl, connectedHeaders);
                    //stompClient.connect(webSocketUrl, sessionHandler).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return (msg);*/
    }


    /*@MessageMapping("/receive")

    //@SendTo("topic/messages")
    public void greeting(String mensaje) throws Exception {
        Console.logInfo("entra","Al menos aquì");
        nodeSubscriptor();
        //messageTemplate.convertAndSend("/topic/messages", message);
        System.out.println("Lo que llega y se envía:" +mensaje );

        //return(message);
    }*/


}