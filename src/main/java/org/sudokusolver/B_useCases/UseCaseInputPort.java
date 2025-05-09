package org.sudokusolver.B_useCases;

import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;

import java.nio.file.Path;
import java.util.List;

public interface UseCaseInputPort {
    List<Cell> loadSudoku(Path sudokuFile);
    void solveOneStep();
    void solveSudokuOneStepInContext();
    void solveByReasoningAsFarAsPossible();
    void reducePossibilitiesFromCurrentState();
    void downloadSudokusFromApiAndStore();
    List<Cell> tryRecursively();
}
