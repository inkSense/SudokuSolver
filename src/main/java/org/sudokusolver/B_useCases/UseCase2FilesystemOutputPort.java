package org.sudokusolver.B_useCases;

import java.util.List;

public interface UseCase2FilesystemOutputPort {
    String loadJsonFile(String sudokuFileName);
    List<String> getAllDataFileNames();
    String getLastLoadedFileName();
    void save(String content, String fileName);
}
