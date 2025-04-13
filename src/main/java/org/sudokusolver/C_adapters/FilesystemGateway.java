package org.sudokusolver.C_adapters;

import org.sudokusolver.B_useCases.UseCase2FilesystemOutputPort;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FilesystemGateway implements UseCase2FilesystemOutputPort {

    public void save(List<String> content, Path path){
        try {
            Files.createDirectories(path.getParent()); // falls übergeordnete Ordner fehlen
            String joined = String.join(System.lineSeparator(), content);
            Files.writeString(path, joined, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Schreiben der Datei", e);
        }
    }

}
