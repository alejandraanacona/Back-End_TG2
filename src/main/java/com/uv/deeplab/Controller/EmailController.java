package com.uv.deeplab.Controller;

import com.uv.deeplab.Service.EmailService;
import com.uv.deeplab.config.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;


@RestController
@RequestMapping("/Email")
public class EmailController {


    @Autowired
    EmailService emailService;

    @GetMapping("/test/")
    public String test(@RequestParam String emailTo) throws IOException, SQLException {
        Console.logInfo("Si cuajo", "Entró a la función");

        String answer = "nothing";
        try {
            Console.logInfo("Si cuajo", "Antes a la función");

            //emailService.sendEmail(emailTo, "TEMA", "CONTENIDO", false, false);
            emailService.sendEmailWithAttachment(emailTo, "Recuperación de contraseña", "Su contraseña es:", "x");
            Console.logInfo("Si cuajo", "Pasó la función de ENVÍO");
            answer = "success";
        } catch (Exception e) {
            Console.logWarn("no cuajo", "ERROR EN ENVÍO");
            answer = "fail";
        }
        return answer;
    }

    @GetMapping("/conection/")
    public String conection(@RequestParam String emailTo) {
        return emailTo;
    }

}
