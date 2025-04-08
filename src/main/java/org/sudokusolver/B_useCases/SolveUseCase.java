package org.sudokusolver.B_useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.*;

import java.util.List;

public class SolveUseCase {
    SudokuBoard sudoku;
    boolean go1 = true;
    boolean go2 = true;
    private static final Logger log = LoggerFactory.getLogger(SolveUseCase.class);

    public void setSudoku(SudokuBoard sudoku) {
        this.sudoku = sudoku;
    }

    public void reducePossibilitiesFromCurrentState(){
        for(Cell cell: sudoku.getCells()){
            int content = cell.content;

            if(content != 0){
                int row = cell.position.y;
                int col = cell.position.x;
                int box = cell.boxIndex;

                cell.removeAllPossibilities();
                resolvePossibilitiesInRow(row, content);
                resolvePossibilitiesInColumn(col, content);
                resolvePossibilitiesInBox(box, content);
            }
        }
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

    public void printOutPossibilities(){
        for(Cell cell : sudoku.getCells()){
            if(!cell.possibleContent.isEmpty()){
                System.out.println(
                        "Möglichkeiten in der Zelle "
                                + cell.position.y + ", "
                                + cell.position.x + ": "
                                + cell.possibleContent
                );
            }
        }
    }

    public void testForSinglePossibilitiesAndFillIn(){
        boolean nothingFound = true;
        for(Cell cell: sudoku.getCells()){
            if(cell.possibleContent.size() == 1){
                nothingFound = false;
                cell.content = cell.possibleContent.get(0);
                cell.removeAllPossibilities();
                log.info("Found " + cell.content + " in " + cell.position.y + "," + cell.position.x);
            }
        }
        if(nothingFound){
            go1 = false;
            log.info("Found nothing to fill in");
        }
    }

    public void testForSinglePossibilitiesInContextAndFillIn(){
        boolean nothingFound = true;
        for(Cell cell: sudoku.getCells()){
                for(Integer i : cell.possibleContent ){
                    int row = cell.position.y;
                    int col = cell.position.x;
                    int box = cell.boxIndex;

                    if( isSinglePossibilityInRow(row, cell, i) ||
                        isSinglePossibilityInColumn(col, cell, i) ||
                        isSinglePossibilityInBox(box, cell, i)
                    ){
                        cell.content = i;
                        cell.removeAllPossibilities();
                        nothingFound = false;
                        reducePossibilitiesFromCurrentState();
                        break;
                    }

            }
        }
        if(nothingFound){
            go2 = false;
            log.info("Found nothing to fill in with Context");
        }
    }

    private boolean isSinglePossibilityInRow(int row, Cell selfCell, int possibilityValue){
        List<Cell> rowWithoutItsself = sudoku.getRow(row);
        rowWithoutItsself.remove(selfCell);
        for(Cell cell : rowWithoutItsself){
            if (cell.possibleContent.contains(possibilityValue) && cell != selfCell) {
                return false;
            }
        }
        return true;
    }

    private boolean isSinglePossibilityInColumn(int col, Cell selfCell, int possibilityValue){
        List<Cell> colWithoutItsself = sudoku.getColumn(col);
        colWithoutItsself.remove(selfCell);
        for(Cell cell : colWithoutItsself){
            if (cell.possibleContent.contains(possibilityValue) && cell != selfCell) {
                return false;
            }
        }
        return true;
    }

    private boolean isSinglePossibilityInBox(int boxIndex, Cell selfCell, int possibilityValue){
        List<Cell> cells = sudoku.getBlock(boxIndex);
        cells.remove(selfCell);
        for (Cell cell : cells) {
            if(cell.possibleContent.contains(possibilityValue)){
                return false;
            }
        }
        return true;
    }

}
