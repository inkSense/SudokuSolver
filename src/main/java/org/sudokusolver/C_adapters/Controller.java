package org.sudokusolver.C_adapters;


import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.B_useCases.UseCaseOutputPort;

import java.util.List;

public class Controller {

    UseCaseOutputPort useCaseOutputPort;

    public Controller(UseCaseOutputPort useCaseOutputPort) {
        this.useCaseOutputPort = useCaseOutputPort;
    }

    public void downloadSudokuAndPrintItOut(){
        SudokuBoard board = useCaseOutputPort.getSudoku();
        board.print();
    }

    public List<SudokuBoard> loadSudokuBoards(){
        return null;
    }
}
