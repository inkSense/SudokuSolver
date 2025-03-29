package org.sudokusolver.B_useCases;

import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.C_adapters.AdapterOutputPort;

import java.util.List;

public class DownloadSudokuFromApiUseCase implements UseCaseInputPort {
    private final UseCaseOutputPort provider;

    public DownloadSudokuFromApiUseCase(UseCaseOutputPort provider) {
        this.provider = provider;
    }

    @Override
    public SudokuBoard getSudoku() {
        return null;
    }

    @Override
    public List<SudokuBoard> loadSudokus() {
        SudokuBoard sudoku = provider.getSudoku();
        List<SudokuBoard> sudokus = List.of(sudoku);
        return sudokus;
    }

    public String downloadJsonString(){
        return provider.fetchSudokuJsonString();
    }
}
