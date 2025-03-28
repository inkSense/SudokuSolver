package org.sudokusolver.C_adapters;

import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

public interface AdapterOutputPort {
    //void displaySudoku(/* Übergib hier später z. B. eine 2D-Cell-Matrix */);
    SudokuBoard fetchSudoku();
}
