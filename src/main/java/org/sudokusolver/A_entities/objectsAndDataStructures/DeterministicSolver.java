package org.sudokusolver.A_entities.objectsAndDataStructures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.awt.Point;

public class DeterministicSolver {
    SudokuBoard sudoku;
    private static final Logger log = LoggerFactory.getLogger(DeterministicSolver.class);

    public void reducePossibilitiesFromCurrentState(SudokuBoard sudoku){
        this.sudoku = sudoku;
        for(Cell cell: sudoku.getCells()){
            int content = cell.getContent();

            if(content != 0){
                int row = cell.getPosition().y;
                int col = cell.getPosition().x;
                int box = cell.getBoxIndex();

                cell.removeAllPossibilities();
                resolvePossibilitiesInRow(row, content);
                resolvePossibilitiesInColumn(col, content);
                resolvePossibilitiesInBox(box, content);
            }
        }
    }

    public void printOutPossibilities(SudokuBoard sudoku){
        this.sudoku = sudoku;
        for(Cell cell : sudoku.getCells()){
            if(!cell.getPossibleContent().isEmpty()){
                System.out.println(
                        "Möglichkeiten in der Zelle "
                                + cell.getPosition().y + ", "
                                + cell.getPosition().x + ": "
                                + cell.getPossibleContent()
                );
            }
        }
    }



    public void solveByReasoningAsFarAsPossible(SudokuBoard sudoku){
        this.sudoku = sudoku;
        boolean singleFound;
        boolean contextFound;
        for(int i = 0; i < 80; i++){
            singleFound = testForSinglePossibilitiesAndFillIn();
            reducePossibilitiesFromCurrentState(sudoku);
            contextFound = testForSinglePossibilitiesInContextAndFillIn(sudoku);
            reducePossibilitiesFromCurrentState(sudoku);
            if(!singleFound && !contextFound){
                log.info("Cant solve anything more with simple reasoning.");
                return;
            }
        }
        log.error("solveByReasoningAsFarAsPossible() ran far too often.");
    }

    public boolean testForSinglePossibilitiesAndFillIn(){
        boolean somethingFound = false;
        for(Cell cell: sudoku.getCells()){
            if(cell.getPossibleContent().size() == 1){
                somethingFound = true;
                cell.setContent(  cell.getPossibleContent().get(0) );
                cell.removeAllPossibilities();
                //log.info("Found " + cell.content + " in " + cell.position.y + "," + cell.position.x);
            }
        }
        return somethingFound;
    }

    public boolean testForSinglePossibilitiesInContextAndFillIn(SudokuBoard sudoku){
        this.sudoku = sudoku;
        boolean somethingFound = false;
        for(Cell cell: sudoku.getCells()){
                for(Integer i : cell.getPossibleContent() ){
                    int row = cell.getPosition().y;
                    int col = cell.getPosition().x;
                    int box = cell.getBoxIndex();

                    if( isSinglePossibilityInRow(row, cell, i) ||
                        isSinglePossibilityInColumn(col, cell, i) ||
                        isSinglePossibilityInBox(box, cell, i)
                    ){
                        cell.setContent(i);
                        cell.removeAllPossibilities();
                        somethingFound = true;
                        reducePossibilitiesFromCurrentState(sudoku);
                        break;
                    }

            }
        }
        return somethingFound;
    }

    private void resolvePossibilitiesInRow(int row, int contentValue){
        for(Cell cell : sudoku.getRow(row)){
            cell.removeFromPossibleContent(contentValue);
        }
    }

    private void resolvePossibilitiesInColumn(int col, int contentValue){
        for(Cell cell : sudoku.getColumn(col)){
            cell.removeFromPossibleContent(contentValue);
        }
    }

    private void resolvePossibilitiesInBox(int boxIndex, int contentValue){
        for(Cell cell : sudoku.getBlock(boxIndex)){
            cell.removeFromPossibleContent(contentValue);
        }
    }

    private boolean isSinglePossibilityInRow(int row, Cell selfCell, int possibilityValue){
        List<Cell> rowWithoutItself = sudoku.getRow(row);
        rowWithoutItself.remove(selfCell);
        for(Cell cell : rowWithoutItself){
            if (cell.getPossibleContent().contains(possibilityValue) && cell != selfCell) {
                return false;
            }
        }
        return true;
    }

    private boolean isSinglePossibilityInColumn(int col, Cell selfCell, int possibilityValue){
        List<Cell> colWithoutItself = sudoku.getColumn(col);
        colWithoutItself.remove(selfCell);
        for(Cell cell : colWithoutItself){
            if (cell.getPossibleContent().contains(possibilityValue) && cell != selfCell) {
                return false;
            }
        }
        return true;
    }

    private boolean isSinglePossibilityInBox(int boxIndex, Cell selfCell, int possibilityValue){
        List<Cell> cells = sudoku.getBlock(boxIndex);
        cells.remove(selfCell);
        for (Cell cell : cells) {
            if(cell.getPossibleContent().contains(possibilityValue)){
                return false;
            }
        }
        return true;
    }


}
