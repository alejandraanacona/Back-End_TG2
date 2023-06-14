package com.uv.deeplab.Service;


//import com.uv.deeplab.Repository.DesenrolamientoRepository;
import com.uv.deeplab.config.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
@EnableScheduling
public class EmailService {

    //@Autowired
    //DesenrolamientoRepository desenrolamientoRepository;

    @Value("${spring.mail.username}")
    String emailFrom;


    public static final String TIEMPOTOTAL = "==>Tiempo total de ejecución: ";

    //@Autowired
    public JavaMailSender javaMailSender;

    public EmailService(
            JavaMailSender javaMailSender
    ) {
        this.javaMailSender = javaMailSender;
    }

//LA UNICA FUNCIÓN QUE SE ESTÁ UTILIZANDO
    public void sendEmailWithAttachment(String toEmail,
                                        String subject,
                                        String body,
                                        String attachment) throws MessagingException {


        Console.logInfo("Si cuajo", "Entró a la función CLAVE");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        Console.logInfo("Si cuajo", "Se prepara mimeMessage");

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        Console.logInfo("Si cuajo", "Se prepara setFrom");

        mimeMessageHelper.setFrom(emailFrom);
        Console.logInfo("Si cuajo", "Se prepara setTo");

        mimeMessageHelper.setTo(toEmail);
        Console.logInfo("Si cuajo", "Se prepara setText");

        mimeMessageHelper.setText(body);
        Console.logInfo("Si cuajo", "Se prepara setSubject");

        mimeMessageHelper.setSubject(subject);

        Console.logInfo("Si cuajo", "ANTES DEL ENVIO");

        javaMailSender.send(mimeMessage);
        Console.logInfo("Si cuajo", "DESPUES DEL ENVIO");

    }
}
