package org.sudokusolver.B_useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Path;
import java.util.List;


public class LoadFileUseCase {

    private static final Logger log = LoggerFactory.getLogger(LoadFileUseCase.class);
    private final UseCase2FilesystemOutputPort useCase2FilesystemOutputPort;

    public LoadFileUseCase(UseCase2FilesystemOutputPort useCase2FilesystemOutputPort) {
        this.useCase2FilesystemOutputPort = useCase2FilesystemOutputPort;
    }

    public String loadJsonFile(String sudokuFileName){
        return useCase2FilesystemOutputPort.loadJsonFile(sudokuFileName);
    }

    List<String> getAllDataFileNames(){
        return useCase2FilesystemOutputPort.getAllDataFileNames();
    }

    public Path getLastLoadedFile(){
        return useCase2FilesystemOutputPort.getLastLoadedFile();
    }


}
