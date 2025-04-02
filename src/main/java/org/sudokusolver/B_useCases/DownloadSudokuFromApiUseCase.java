package org.sudokusolver.B_useCases;

import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

import java.util.List;

public class DownloadSudokuFromApiUseCase {
    private final UseCaseOutputPort provider;

    public DownloadSudokuFromApiUseCase(UseCaseOutputPort provider) {
        this.provider = provider;
    }


    public List<String> downloadJsonStrings(){
        return provider.getSudokuJsonStrings();
    }
}
