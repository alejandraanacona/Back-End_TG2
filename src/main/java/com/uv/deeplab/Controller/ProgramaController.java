package com.uv.deeplab.Controller;


import com.uv.deeplab.Entities.Programas;
import com.uv.deeplab.Service.ProgramasService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/userfolders")
@AllArgsConstructor
public class ProgramaController {

    private ProgramasService programasService;

    @PostMapping
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
    }

}
