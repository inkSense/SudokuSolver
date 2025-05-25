package org.sudokusolver.B_useCases;

import org.sudokusolver.A_entities.objects.Cell;
import org.sudokusolver.A_entities.dataStructures.Position;

import java.util.List;

public interface UseCaseInputPort {
    List<Cell> loadSudoku(String sudokuFileName);
    List<String> getAllDataFileNames();
    String getLastLoadedFileName();
    void saveSudoku(String fileName);
    void downloadSudokuFromApiAndStore();
    void reducePossibilitiesFromCurrentState();
    void solveOneStep();
    void solveSudokuOneStepInUnit();
    void solveByReasoningAsFarAsPossible();
    List<Cell> solveRecursively();
    void handleKeyInputWithCellClickedAtPosition(int value, Position clickedCell);
    List<Cell> validateSudoku();
}
