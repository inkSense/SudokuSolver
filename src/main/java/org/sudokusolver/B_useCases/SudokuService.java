package org.sudokusolver.B_useCases;

import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.C_adapters.AdapterOutputPort;

public class SudokuService implements UseCaseOutputPort {
    private final AdapterOutputPort provider;

    public SudokuService(AdapterOutputPort provider) {
        this.provider = provider;
    }

    @Override
    public SudokuBoard getSudoku() {
        return provider.fetchSudoku();
    }
}
