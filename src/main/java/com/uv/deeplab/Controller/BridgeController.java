package com.uv.deeplab.Controller;

import com.uv.deeplab.Service.SupportFunctions.ReadPython;
import com.uv.deeplab.Service.SupportFunctions.SubscriptorsRos;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/bridge")
public class BridgeController {


    public ReadPython readPython;
    public SubscriptorsRos subscriptorRos;
   /* @PostMapping("/python/")
    public ResponseEntity<String> bridgePython() throws IOException, SQLException {
        Console.logInfo("llega peticiòn"," de correr archivo python");
        //String mensaje = subscriptorRos.nodeSubscriptor();
        ResponseEntity.ok("nada");
        Console.logInfo("Si envió", "mensaje: "+ mensaje);
        return ResponseEntity.ok(mensaje);
    }*/
}

