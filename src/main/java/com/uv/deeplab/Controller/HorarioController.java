package com.uv.deeplab.Controller;


import com.uv.deeplab.Entities.Horario;
import com.uv.deeplab.Service.HorarioMessage;
import com.uv.deeplab.Service.Horarioservice;
import com.uv.deeplab.config.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/horario")
@ComponentScan
public class HorarioController {

    @Autowired
    private Horarioservice horarioservice;

    // Endpoint para crear una nueva sesión
    @PostMapping("/reservar")
    public ResponseEntity<HorarioMessage> reservarHorario(@RequestBody Horario horario) {
        HorarioMessage result = horarioservice.reservarHorario(horario);
        if (result.getMessage().contains("exitosamente")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    // Endpoint para obtener todas las sesiones
   /* @GetMapping
    public ResponseEntity<List<Horario>> getHorarios() {
        List<Horario> sesiones = horarioservice.listaHorarios();
        return ResponseEntity.ok(sesiones);
    }*/
    @GetMapping("/{userId}")
    public ResponseEntity<List<Horario>> obtenerHorario(@PathVariable Long userId) {
        List<Horario> horario = horarioservice.obtenerHorario(userId);
        Console.logInfo("lo que ", "consulta horario" + horario);
        if (horario != null && !horario.isEmpty()) {
            return ResponseEntity.ok(horario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
