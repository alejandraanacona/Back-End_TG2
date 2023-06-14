package com.uv.deeplab.Controller;

import com.uv.deeplab.Dto.DActividades;
import com.uv.deeplab.Dto.DUsuarios;
import com.uv.deeplab.Service.ActividadesService;
import com.uv.deeplab.Service.LoginMesage;
import com.uv.deeplab.Service.UsuariosService;
import com.uv.deeplab.config.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;


@RestController
//@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/activities")
public class ActividadesController {

    @Autowired
    ActividadesService actividadesService;

    @PostMapping("/save/")
    public void saveActividades(@RequestBody DActividades dActividades){
        Console.logInfo("ActividadesController", "Se obtiene la siguiente actividad del front: "+dActividades);
        actividadesService.actividadesSave(dActividades);
    }

    @PostMapping("/update/")
    public void updateActividades(@RequestBody DActividades dActividades){
        Console.logInfo("ActividadesController", "Se obtiene la siguiente actividad del front: "+dActividades);
        actividadesService.actividadesUpdate(dActividades);
    }
}
