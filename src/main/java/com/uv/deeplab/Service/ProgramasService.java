package com.uv.deeplab.Service;

import com.uv.deeplab.Dto.DParametros;
import com.uv.deeplab.Entities.Programas;
import com.uv.deeplab.Repository.ProgramasRepository;
import com.uv.deeplab.Service.SupportFunctions.FileSystemService;
import com.uv.deeplab.config.Console;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ProgramasService {


    private ProgramasRepository programasRepository;

    private FileSystemService fileSystemService;


    public void createPkg (Programas programas){
        Console.logInfo("createPkg", "Se inicia solicitud de creación de pkg");

        String namePkg = programas.getPkgName();

    }


    public Programas createUserFolder(Programas programas) throws IOException {
        // Crear la carpeta física en el servidor
        String folderPath = "~/wssDeepLabUV" + programas.getUserId() + "/" + programas.getNameFolder();
        fileSystemService.createFolder(folderPath);
        programas.setPath(folderPath);

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

}
