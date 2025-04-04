package org.sudokusolver.B_useCases;

import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

import java.util.List;

public interface UseCaseInputPort {
    void setSudoku(SudokuBoard sudoku);
    List<SudokuBoard> loadSudokus();
    void solveOneStep();

    void reducePossibilitiesFromCurrentState();
}
