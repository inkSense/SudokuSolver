package org.sudokusolver.C_adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.B_useCases.UseCase2FilesystemOutputPort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilesystemGateway implements UseCase2FilesystemOutputPort {
    private static final Logger log = LoggerFactory.getLogger(FilesystemGateway.class);
    Path folderPath = Path.of(AdapterConf.dataFolderPath);
    private String lastLoadedFileName = "";

    public String loadJsonFile(String sudokuFileName){
        Path filePath = folderPath.resolve(sudokuFileName);
        if (!Files.exists(filePath)) {
            log.error("File doesn't exist: " + filePath);
            return "";
        }
        try {
            String fileString = Files.readString(filePath);
            lastLoadedFileName = sudokuFileName;
            return fileString;
        } catch (Exception e) {
            throw new RuntimeException("Error reading file " + filePath, e);
        }
    }

    public List<String> getAllDataFileNames(){
        try (var paths = Files.list(folderPath)) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch(IOException e){
            log.error("Wrong in getFileNames()");
            return new ArrayList<>();
        }
    }

    public String getLastLoadedFileName(){
        return lastLoadedFileName;
    }

    public void save(String contentString, String fileName){
        Path savePath = this.folderPath.resolve(fileName);
        try {
            Files.createDirectories(this.folderPath);
            Files.writeString(savePath, contentString, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Schreiben der Datei", e);
        }
    }

}
