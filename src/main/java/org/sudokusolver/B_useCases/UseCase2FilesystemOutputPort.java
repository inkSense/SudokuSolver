package org.sudokusolver.B_useCases;

import java.nio.file.Path;
import java.util.List;

public interface UseCase2FilesystemOutputPort {
    void save(List<String> content, Path path);
}
