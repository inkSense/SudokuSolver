package org.sudokusolver.B_useCases;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class SaveFileUseCase {
    Path path = Path.of(ApplicationConf.dataPathString);
    void save(List<String> content) {
        try {
            Files.createDirectories(path.getParent()); // falls übergeordnete Ordner fehlen
            String joined = String.join(System.lineSeparator(), content);
            Files.writeString(path, joined, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Schreiben der Datei", e);
        }
    }
}
