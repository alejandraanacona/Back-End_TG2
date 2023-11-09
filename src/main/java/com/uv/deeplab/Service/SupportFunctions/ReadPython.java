package com.uv.deeplab.Service.SupportFunctions;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import com.uv.deeplab.config.Console;
import org.springframework.stereotype.Service;

import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReadPython {

    /*public static void main(String[] args) {
        readPython2("python3 /home/servidor/Documentos/test.py");
    }*/

    //String cmd="python3 /home/servidor/Documentos/test.py";
    public static String readPython2(String cmd) {

        Console.logInfo("Està entrando", "a la funciòn de python");
        String line = "";
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command
            //System.out.println("Here is the standard output of the command:\n");
            String outputLines;
            while ((outputLines = stdInput.readLine()) != null) {
                line=outputLines;
                Console.logInfo("Està leyendo la salida", "del archivo python: " + line);
                //System.out.println(s);
            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((line = stdError.readLine()) != null) {
               Console.logInfo("Està leyendo la salida", "del archivo python: " + line);
            }

            //System.exit(0);
        } catch (IOException e) {
            //System.out.println("exception happened - here's what I know: ");
            Console.logInfo("entrò en excepcion", "No ejecutò");

            e.printStackTrace();
            //System.exit(-1);
        }
        return (line);
    }
}


