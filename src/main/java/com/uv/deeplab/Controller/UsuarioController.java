package com.uv.deeplab.Controller;

import com.uv.deeplab.Dto.DUsuarios;
import com.uv.deeplab.Service.EmailService;
import com.uv.deeplab.Service.LoginMesage;
import com.uv.deeplab.Service.UsuariosService;
import com.uv.deeplab.config.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    UsuariosService usuariosService;

    @PostMapping("/login/")
    public ResponseEntity<?> login(@RequestBody DUsuarios dUsuarios) throws IOException, SQLException {
        Console.logInfo("Lo que llega", "usuario del front"+dUsuarios);
        LoginMesage loginMesage = usuariosService.loginMesage(dUsuarios);
        Console.logInfo("Si cuajo", "Entró a la función");
        return ResponseEntity.ok(loginMesage);
    }
}
