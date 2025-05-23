package org.sudokusolver.B_useCases;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SaveFileUseCase {
    UseCase2FilesystemOutputPort useCase2FilesystemOutputPort;
    private static final Logger log = LoggerFactory.getLogger(SaveFileUseCase.class);

    public SaveFileUseCase(UseCase2FilesystemOutputPort useCase2FilesystemOutputPort) {
        this.useCase2FilesystemOutputPort = useCase2FilesystemOutputPort;
    }

    void saveToJsonFileNamedByDate(String content){
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd HHmmss"));
        String fileString = "sudoku " +  dateTime  + ".json";
        saveToJsonFile(content, fileString);
    }

    void saveToJsonFile(String content, String fileName){
        useCase2FilesystemOutputPort.save(content, fileName);
    }
}
