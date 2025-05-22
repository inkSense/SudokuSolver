package org.sudokusolver.B_useCases;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SaveFileUseCase {
    Path path = Path.of(ApplicationConf.dataFolderPath);
    UseCase2FilesystemOutputPort useCase2FilesystemOutputPort;

    public SaveFileUseCase(UseCase2FilesystemOutputPort useCase2FilesystemOutputPort) {
        this.useCase2FilesystemOutputPort = useCase2FilesystemOutputPort;

    }

    void saveToJsonFileNamedByDate(String content){
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd HHmmss"));
        String dateTimePathString = this.path + "/sudoku " +  dateTime  + ".json" ;
        Path dateTimePath = Path.of(dateTimePathString);
        List<String> stringList = new ArrayList<>();
        stringList.add(content);
        useCase2FilesystemOutputPort.save(stringList, dateTimePath);
    }

    void saveToJsonFile(String content, String fileName){
        String pathString = this.path + "/" + fileName + ".json";
        path = Path.of(pathString);
        List<String> stringList = new ArrayList<>();
        stringList.add(content);
        useCase2FilesystemOutputPort.save(stringList, path);
    }
}
