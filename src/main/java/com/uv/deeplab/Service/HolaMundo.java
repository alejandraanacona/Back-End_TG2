package com.uv.deeplab.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HolaMundo {
    String mensajeHola =" ";
    private int contador=0;
    @Scheduled(fixedRate = 1000) // Ejecuta cada segundo (1000 milisegundos)
    public String printHelloWorld() {
        mensajeHola= "Hola mundo"+ contador;
        return(mensajeHola);
    }

    public String getMensajeHola() {
        return mensajeHola;
    }

    public void setMensajeHola(String mensajeHola) {
        this.mensajeHola = mensajeHola;
    }

}
