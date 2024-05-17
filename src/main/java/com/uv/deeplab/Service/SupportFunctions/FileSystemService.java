package com.uv.deeplab.Service.SupportFunctions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import java.io.IOException;

@Service
public class FileSystemService {

    public void createFolder(String folderPath) throws IOException {
        Path path = Paths.get(folderPath);
        Files.createDirectories(path);
    }

    public void deleteFolder(String folderPath) throws IOException {
        Path path = Paths.get(folderPath);
        Files.deleteIfExists(path);
    }

    public void moveFolder(String sourceFolderPath, String targetFolderPath) throws IOException {
        Path sourcePath = Paths.get(sourceFolderPath);
        Path targetPath = Paths.get(targetFolderPath);
        Files.move(sourcePath, targetPath);
    }

    public List<String> listFilesInFolder(String folderPath) throws IOException {
        try (var files = Files.list(Paths.get(folderPath))) {
            return files.map(Path::toString).collect(Collectors.toList());
        }
    }

    // Otros métodos para administrar archivos y carpetas
}
