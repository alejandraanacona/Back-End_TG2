package com.uv.deeplab.Controller;


import com.uv.deeplab.Entities.Horario;
import com.uv.deeplab.Entities.Programas;
import com.uv.deeplab.Service.ProgramasService;
import com.uv.deeplab.config.Console;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/userfolders")
@AllArgsConstructor
public class ProgramaController {

    private ProgramasService programasService;

   /* @PostMapping
    public Programas createUserFolder(@RequestBody Programas programas) throws IOException {
        return programasService.createUserFolder(programas);
    }

    @PutMapping("/{id}")
    public Programas updateUserFolder(@PathVariable Long id, @RequestBody Programas programas) throws Exception {
        return programasService.updateUserFolder(id, programas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserFolder(@PathVariable Long id) throws Exception {
        programasService.deleteUserFolder(id);
        return ResponseEntity.noContent().build();
    }*/

    @GetMapping("/{userId}")
    public ResponseEntity<String> obtenerArchivo(@PathVariable Long userId) throws Exception {
        String path = programasService.consultarPath(userId);
        Console.logInfo("el path", "que consulto " + path);
        try {
            Path filePath = Paths.get(path);
            Console.logInfo("el contenido", "que consulto " + filePath);

            String content = new String(Files.readAllBytes(filePath));
            Console.logInfo("el contenido", "que consulto " + content);
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            Console.logInfo("responde error", "malo " +e.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al leer el archivo: " + e.getMessage());
        }
    }

}
