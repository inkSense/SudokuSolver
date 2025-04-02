package org.sudokusolver.C_adapters;

import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.B_useCases.UseCaseOutputPort;

import java.util.Collections;
import java.util.List;

public class FilesystemGateway implements UseCaseOutputPort {

    @Override
    public List<SudokuBoard> getSudokus() {
        return loadSudokusFromDisk();
    }

    @Override
    public List<String> getSudokuJsonStrings() {
        return Collections.EMPTY_LIST;
    }

    private List<SudokuBoard> loadSudokusFromDisk(){
        return Collections.EMPTY_LIST;
    }
}
