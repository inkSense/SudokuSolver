package org.sudokusolver.B_useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;
import java.util.Collections;
import java.util.List;

public class LoadFileUseCase {

    private static final Logger log = LoggerFactory.getLogger(LoadFileUseCase.class);

    public List<String> loadJsonStrings() {
        // Läd Dateien mit mehreren Sudoku-Strings
        Path filePath = Paths.get(ApplicationConf.dataFolderPath);
        if (!Files.exists(filePath)) {
            log.error("File doesn't exist: " + filePath);
            return Collections.emptyList();
        }
        try {
            return Files.readAllLines(filePath); // <- jede Zeile als Eintrag in der Liste
        } catch (Exception e) {
            throw new RuntimeException("Error reading file " + filePath, e);
        }
    }

    public String loadJsonFile(Path filePath){
        if (!Files.exists(filePath)) {
            log.error("File doesn't exist: " + filePath);
            return "";
        }
        try {
            return Files.readString(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Error reading file " + filePath, e);
        }
    }



}
