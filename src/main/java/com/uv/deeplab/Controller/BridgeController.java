package com.uv.deeplab.Controller;

import com.uv.deeplab.Dto.DUsuarios;
import com.uv.deeplab.Service.LoginMesage;
import com.uv.deeplab.Service.SupportFunctions.ReadPython;
import com.uv.deeplab.Service.SupportFunctions.SubscriptorRos;
import com.uv.deeplab.Service.UsuariosService;
import com.uv.deeplab.config.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/bridge")
public class BridgeController {


    public ReadPython readPython;
    public SubscriptorRos subscriptorRos;
   /* @PostMapping("/python/")
    public ResponseEntity<String> bridgePython() throws IOException, SQLException {
        Console.logInfo("llega peticiòn"," de correr archivo python");
        //String mensaje = subscriptorRos.nodeSubscriptor();
        ResponseEntity.ok("nada");
        Console.logInfo("Si envió", "mensaje: "+ mensaje);
        return ResponseEntity.ok(mensaje);
    }*/
}

