package com.uv.deeplab.Controller;


import com.uv.deeplab.Dto.DProgramas;
import com.uv.deeplab.Dto.DUsuarios;
import com.uv.deeplab.Entities.Horario;
import com.uv.deeplab.Entities.Programas;
import com.uv.deeplab.Entities.Usuarios;
import com.uv.deeplab.Service.ParametrosService;
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
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/userfolders")
@AllArgsConstructor
public class ProgramaController {

    private ProgramasService programasService;

    private ParametrosService parametrosService;

   @PostMapping("/save")
    public void createUserFolder(@RequestBody DProgramas dProgramas) throws IOException {
             programasService.writeToFile(dProgramas);
    }

    @PutMapping("/{id}")
    public Programas updateUserFolder(@PathVariable Long id, @RequestBody Programas programas) throws Exception {
        return programasService.updateUserFolder(id, programas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserFolder(@PathVariable Long id) throws Exception {
        programasService.deleteUserFolder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/{archivo}")
    public ResponseEntity<?> obtenerArchivo(@PathVariable Long userId, @PathVariable String archivo) throws Exception {

       Programas programas = programasService.consultarPath(userId);
        String pathBase=programas.getPath();
        String pathNodo=pathBase+"/src/"+programas.getNameFolder()+"/"+programas.getNameFolder()+"/my_node.py";
        String pathSetup=pathBase+"/src/"+programas.getNameFolder()+"/setup.py";
        String path=" ";

        if(archivo.equals("nodo")){
            path=pathNodo;
        }
        else if(archivo.equals("setup")){
            path=pathSetup;
        }
        //String pathLaunch= pathBase+
        Console.logInfo("el path", "que consulto " + path);

        try {
            Path filePath = Paths.get(path);
            Console.logInfo("el contenido", "que consulto " + filePath);

            String content = new String(Files.readAllBytes(filePath));
            Console.logInfo("el contenido", "que consulto " + content);
            return ResponseEntity.ok(DProgramas.builder().path(path).fileContent(content).build());
        } catch (IOException e) {
            Console.logInfo("responde error", "malo " +e.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al leer el archivo: " + e.getMessage());
        }
    }

    @PostMapping("/buildAndRun")
    public ResponseEntity<?> buildAndRunPackage(@RequestBody DUsuarios usuarios) throws Exception {
        Console.logInfo("el userid que llegea", ":" + usuarios.getUserId());

        Programas programas = programasService.consultarPath(usuarios.getUserId());
        String nombrePkg = programas.getNameFolder();
        try {
            Console.logInfo("ParametrosController", "Inicia solicitud de construcción y ejecución del paquete ROS 2");
            parametrosService.buildAndRunPackage(usuarios.getUserId(), nombrePkg);
            return ResponseEntity.ok("salio bien");
        } catch (Exception e) {
            Console.logError("ParametrosController", "Error en la construcción y ejecución del paquete ROS 2: " + e.getMessage());
            throw new RuntimeException("Error en la construcción y ejecución del paquete ROS 2", e);

        }
    }
    @PostMapping("/stop")
    public ResponseEntity<?> stopPrograma()  {
        parametrosService.stopRunningProcess();
        return ResponseEntity.ok("stopped");
    }


}
