package org.sudokusolver.B_useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

import java.nio.file.*;

public class LoadFileUseCase {

    private static final Logger log = LoggerFactory.getLogger(LoadFileUseCase.class);

    public String loadJsonString() {
        Path filePath = Paths.get(Conf.dataPathString);
        // Path filePath = Paths.get("data", "sudoku.json");
        if (!Files.exists(filePath)) {
            log.error("File doesnt exist: " + filePath);
            return null;
        }
        try {
            return Files.readString(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Error reading file " + filePath, e);
        }
    }

    public static void main(String[] args){
        var useCase = new LoadFileUseCase();
        String json = useCase.loadJsonString();
        log.info(json);
    }

}
