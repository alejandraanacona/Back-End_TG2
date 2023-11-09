package com.uv.deeplab.Service.SupportFunctions;

import edu.wpi.rail.jrosbridge.Ros;
import edu.wpi.rail.jrosbridge.Topic;
import edu.wpi.rail.jrosbridge.callback.TopicCallback;
import edu.wpi.rail.jrosbridge.messages.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;
@Service
public class SubscriptorRos {

    //*public static void main(String[] args) throws InterruptedException {
    /*
    public static String nodeSubscriptor() {
        // Connect to the ROS server
        Ros ros = new Ros("localhost");
        ros.connect();

        Topic echoBack = new Topic(ros, "/topic", "std_msgs/String");
        final AtomicReference<String> messageString = new AtomicReference<>();

        echoBack.subscribe(new TopicCallback() {
            @Override
            public void handleMessage(Message message) {
                messageString.set(message.toString());
                System.out.println("From ROS: " + message.toString());

            }
        });

        // Keep the application alive to listen to messages
        try {
            Thread.sleep(100000); // Sleep for 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Disconnect from ROS
        //ros.disconnect();
        return(messageString.get());
    }*/

}
