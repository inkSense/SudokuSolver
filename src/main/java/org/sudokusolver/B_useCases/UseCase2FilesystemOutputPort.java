package org.sudokusolver.B_useCases;

import java.nio.file.Path;
import java.util.List;

public interface UseCase2FilesystemOutputPort {
    String loadJsonFile(String sudokuFileName);
    List<String> getAllDataFileNames();
    Path getLastLoadedFile();
    void save(String content, String fileName);
}
