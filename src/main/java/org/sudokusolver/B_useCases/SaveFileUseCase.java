package org.sudokusolver.B_useCases;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SaveFileUseCase {
    Path path = Path.of(ApplicationConf.dataPathString);
    UseCase2FilesystemOutputPort useCase2FilesystemOutputPort;

    public SaveFileUseCase(UseCase2FilesystemOutputPort useCase2FilesystemOutputPort) {
        this.useCase2FilesystemOutputPort = useCase2FilesystemOutputPort;

    }

    void save(List<String> content) {
        useCase2FilesystemOutputPort.save(content, path);
    }

    void saveToJsonFileNamedByDate(String content){
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd HHmmss"));
        Path path = Path.of(ApplicationConf.dataPathString);
        String dateTimePathString = path + "/sudoku " +  dateTime  + ".json" ;
        Path dateTimePath = Path.of(dateTimePathString);
        List<String> stringList = new ArrayList<>();
        stringList.add(content);
        useCase2FilesystemOutputPort.save(stringList, dateTimePath);
    }
}
