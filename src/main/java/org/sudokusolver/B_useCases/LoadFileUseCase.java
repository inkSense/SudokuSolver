package org.sudokusolver.B_useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LoadFileUseCase {

    private static final Logger log = LoggerFactory.getLogger(LoadFileUseCase.class);
    private Path lastLoadedFile;

    public List<String> loadJsonStrings() {
        // Läd Dateien mit mehreren Sudoku-Strings
        Path filePath = Paths.get(ApplicationConf.dataFolderPath);
        if (!Files.exists(filePath)) {
            log.error("File doesn't exist: " + filePath);
            return Collections.emptyList();
        }
        try {
            return Files.readAllLines(filePath); // jede Zeile als Eintrag in der Liste
        } catch (Exception e) {
            throw new RuntimeException("Error reading file " + filePath, e);
        }
    }

    public String loadJsonFile(String sudokuFileName){
        Path filePath = Paths.get(ApplicationConf.dataFolderPath).resolve(sudokuFileName);
        if (!Files.exists(filePath)) {
            log.error("File doesn't exist: " + filePath);
            return "";
        }
        try {
            String fileString = Files.readString(filePath);
            lastLoadedFile = filePath;
            return fileString;
        } catch (Exception e) {
            throw new RuntimeException("Error reading file " + filePath, e);
        }
    }

    List<String> getAllDataFileNames(){
        try (var paths = Files.list(Paths.get(ApplicationConf.dataFolderPath))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch(IOException e){
            log.error("Wrong in getFileNames()");
            return new ArrayList<>();
        }
    }

    public Path getLastLoadedFile(){
        return lastLoadedFile;
    }



}
