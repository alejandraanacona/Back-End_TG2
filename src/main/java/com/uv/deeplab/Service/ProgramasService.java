package com.uv.deeplab.Service;

import com.uv.deeplab.Dto.DParametros;
import com.uv.deeplab.Dto.DProgramas;
import com.uv.deeplab.Entities.Programas;
import com.uv.deeplab.Repository.ProgramasRepository;
import com.uv.deeplab.Service.SupportFunctions.FileSystemService;
import com.uv.deeplab.config.Console;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@ComponentScan
public class ProgramasService {


    private final ProgramasRepository programasRepository;

    private FileSystemService fileSystemService;


   /* public void createPkg (Programas programas){
        Console.logInfo("createPkg", "Se inicia solicitud de creación de pkg");

        String namePkg = programas.getPkgName();

    }*/


    public Programas savePath(Programas programas) throws IOException {

        return programasRepository.save(programas);
    }

    public Programas updateUserFolder(Long id, Programas updatedFolder) throws Exception {
        Programas folder = programasRepository.findById(id)
                .orElseThrow(() -> new Exception("Carpeta no encontrada"));

        folder.setNameFolder(updatedFolder.getNameFolder());

        // Actualizar la carpeta física en el servidor si es necesario
        if (!folder.getNameFolder().equals(updatedFolder.getNameFolder())) {
            fileSystemService.deleteFolder(folder.getNameFolder());
            fileSystemService.createFolder(updatedFolder.getNameFolder());
            // Eliminar la carpeta anterior y crear una nueva
        }

        return programasRepository.save(folder);
    }

    public void deleteUserFolder(Long id) throws Exception {
        Programas folder = programasRepository.findById(id)
                .orElseThrow(() -> new Exception("Carpeta no encontrada"));
        fileSystemService.deleteFolder(folder.getNameFolder());
        // Eliminar la carpeta física en el servidor si es necesario

        programasRepository.delete(folder);
    }

    public Programas consultarPath(Long userid) throws Exception {
        Programas programas = programasRepository.findPathById(userid);
        return (programas);
    }

    public String writeToFile(DProgramas dProgramas) {
        try (FileWriter writer = new FileWriter(dProgramas.getPath())) {
            writer.write(dProgramas.getFileContent());
            return "File written successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error writing to file: " + e.getMessage();
        }
    }
}
