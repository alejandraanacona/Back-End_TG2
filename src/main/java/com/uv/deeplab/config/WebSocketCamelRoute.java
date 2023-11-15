package com.uv.deeplab.config;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/*@Component
public class WebSocketCamelRoute extends RouteBuilder {
    /*@Override
    public void configure() throws Exception {

        WebSocketComponent wsComponent = new WebSocketComponent();
        wsComponent.setClientSocketFactory(new MyWebSocketClientFactory()); // Reemplaza MyWebSocketClientFactory con tu propia implementación

        getContext().addComponent("websocket", wsComponent);

        // Consumir del WebSocket externo (ajusta la URL según tu configuración)
        from("websocket://localhost:9090/topic")
                .setHeader(WebsocketConstants.SEND_TO_ALL, constant(true))
                .to("websocket://local
                        DefaultWebSocketClientFactory
                        WebSocketComponent wsComponent = new WebSocketComponent();
        wsComponent.setClientSocketFactory(new MyWebSocketClientFactory()); // Reemplaza MyWebSocketClientFactory con tu propia implementación

        getContext().addComponent("websocket", wsComponent);

        // Consumir del WebSocket externo (ajusta la URL según tu configuración)
        from("websocket://localhost:9090/topic")
                .setHeader(WebsocketConstants.SEND_TO_ALL, constant(true))
                .to("websocket://local
}*/

