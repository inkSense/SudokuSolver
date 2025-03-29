package org.sudokusolver.B_useCases;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class SaveFileUseCase {
    Path path = Path.of(Conf.dataPathString);
    void save(String content) {
        try {
            Files.createDirectories(path.getParent()); // falls übergeordnete Ordner fehlen
            Files.writeString(path, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Schreiben der Datei", e);
        }
    }
}
