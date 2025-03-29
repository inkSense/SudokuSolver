package org.sudokusolver.C_adapters;

import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.B_useCases.UseCaseOutputPort;

public class FilesystemGateway implements UseCaseOutputPort {

    @Override
    public SudokuBoard getSudoku() {
        return loadSudokuFromDisk();
    }

    @Override
    public String fetchSudokuJsonString() {
        return "";
    }

    private SudokuBoard loadSudokuFromDisk(){
        return null;
    }
}
