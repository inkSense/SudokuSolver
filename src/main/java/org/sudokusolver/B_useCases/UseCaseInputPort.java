package org.sudokusolver.B_useCases;

import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import java.nio.file.Path;
import java.util.List;
import java.awt.Point;

public interface UseCaseInputPort {
    List<Cell> loadSudoku(Path sudokuFile);
    void solveOneStep();
    void solveSudokuOneStepInUnit();
    void solveByReasoningAsFarAsPossible();
    void reducePossibilitiesFromCurrentState();
    void downloadSudokuFromApiAndStore();
    List<Cell> solveRecursively();
    void handleKeyInputWithCellClickedAtPosition(int value, Point clickedCell);
    void validateSudoku();
}
