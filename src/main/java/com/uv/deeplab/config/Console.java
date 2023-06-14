package com.uv.deeplab.config;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Console: Clase creada para contener las funciones que imprimirán los Logs de Info, Warn y Error
 *Todas las funciones tendrán la misma estructura
 * LogInfo - LogWanr - LogError:
 * @param //String Class: Consta del nombre de la clase en la que se ejecuta el Log
 * @param //String Texto: Consta del texto que se quiere que se muestre en el Log
 * @return void
 * Ej: Console.LogInfo((SimuladorBaseRestController.class.getName()),"hola");
 */

public class Console {

    private Console() {
    }

    private static Logger logger = null;


    public static void logInfo(String nameClass, String texto){
        logger= Logger.getLogger(nameClass);
        logger.log(Level.INFO, texto);
    }

    public static void logWarn(String nameClass, String texto){
        logger= Logger.getLogger(nameClass);
        logger.log(Level.WARNING, texto);
    }
    public static void logError(String nameClass, String texto){
        logger= Logger.getLogger(nameClass);
        logger.log(Level.SEVERE, texto);
    }

}
