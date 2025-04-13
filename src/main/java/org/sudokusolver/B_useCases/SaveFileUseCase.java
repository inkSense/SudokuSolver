package org.sudokusolver.B_useCases;

import java.nio.file.Path;
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
}
