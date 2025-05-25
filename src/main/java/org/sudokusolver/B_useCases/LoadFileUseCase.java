package org.sudokusolver.B_useCases;

import java.util.List;


public class LoadFileUseCase {

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

    public String getLastLoadedFileName(){
        return useCase2FilesystemOutputPort.getLastLoadedFileName();
    }


}
