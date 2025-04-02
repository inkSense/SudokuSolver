package org.sudokusolver.B_useCases;

import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

import java.util.List;

public interface UseCaseOutputPort {
    List<SudokuBoard> getSudokus();
    List<String> getSudokuJsonStrings();
}
