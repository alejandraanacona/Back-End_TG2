package com.uv.deeplab.Controller;

import com.uv.deeplab.Dto.DUsuarios;
import com.uv.deeplab.Service.CreateMessage;
import com.uv.deeplab.Service.LoginMesage;
import com.uv.deeplab.Service.SesionService;
import com.uv.deeplab.Service.SupportFunctions.SubscriptorsRos;
import com.uv.deeplab.Service.UsuariosService;
import com.uv.deeplab.config.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;


@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/user")
@ComponentScan
public class UsuarioController {

    @Autowired
    UsuariosService usuariosService;



    @Autowired
    SubscriptorsRos subscriptorRos;

    @PostMapping("/login/")
    public ResponseEntity<?> login(@RequestBody DUsuarios dUsuarios) throws IOException, SQLException, ExecutionException, InterruptedException {
        subscriptorRos.connectToRos();
        Console.logInfo("Lo que llega", "usuario del front"+dUsuarios);
        LoginMesage loginMesage = usuariosService.loginMesage(dUsuarios);
        Console.logInfo("Si cuajo", "Entró a la función" + loginMesage);
        return ResponseEntity.ok(loginMesage);
    }
   @PostMapping("/save/")
   public ResponseEntity<?> register(@RequestBody DUsuarios dUsuarios) throws IOException, SQLException, ExecutionException, InterruptedException, MessagingException {
        Console.logInfo("usuarioService", "usuario que viene del front: "+dUsuarios);
        CreateMessage createMessage = usuariosService.create(dUsuarios);
        Console.logInfo("Guardó el dato", "Exitosamente");
        return ResponseEntity.ok(createMessage);
    }
}
