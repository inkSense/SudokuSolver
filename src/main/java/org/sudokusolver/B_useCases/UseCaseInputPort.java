package org.sudokusolver.B_useCases;

import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.A_entities.objectsAndDataStructures.Position;
import java.nio.file.Path;
import java.util.List;

public interface UseCaseInputPort {
    List<Cell> loadSudoku(Path sudokuFile);
    void solveOneStep();
    void solveSudokuOneStepInUnit();
    void solveByReasoningAsFarAsPossible();
    void reducePossibilitiesFromCurrentState();
    void downloadSudokuFromApiAndStore();
    List<Cell> solveRecursively();
    void handleKeyInputWithCellClickedAtPosition(int value, Position clickedCell);
    List<Cell> validateSudoku();
}
