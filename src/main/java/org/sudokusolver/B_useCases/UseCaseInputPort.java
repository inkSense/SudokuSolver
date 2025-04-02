package org.sudokusolver.B_useCases;

import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

import java.util.List;

public interface UseCaseInputPort {
    List<SudokuBoard> loadSudokus();
    void solve(SudokuBoard sudoku);
}
