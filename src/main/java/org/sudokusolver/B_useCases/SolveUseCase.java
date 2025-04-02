package org.sudokusolver.B_useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

import java.util.List;

public class SolveUseCase {
    SudokuBoard sudoku;
    Cell[][] grid;
    boolean go1 = true;
    boolean go2 = true;
    private static final Logger log = LoggerFactory.getLogger(SolveUseCase.class);

    public void setSudoku(SudokuBoard sudoku) {
        this.sudoku = sudoku;
        grid = sudoku.getCells();
    }

    public void reducePossibilitiesFromCurrentState(){
        for(int row = 0; row < 9; row++ ){
            for(int col = 0; col < 9; col++){
                Cell cell = grid[row][col];
                int content = cell.content;
                if(content != 0){
                    cell.removeAllPossibilities();
                    resolvePossibilitiesInRow(row, content);
                    resolvePossibilitiesInColumn(col, content);
                    int box = sudoku.getBlockIndexFromCell(row, col);
                    resolvePossibilitiesInBox(box, content);
                }
            }
        }
    }

    private void resolvePossibilitiesInRow(int row, int contentValue){
        for(int col = 0; col < 9; col++) {
            Cell cell = grid[row][col];
            cell.removeFromPossibleContent(contentValue);
        }
    }

    private void resolvePossibilitiesInColumn(int col, int contentValue){
        for(int row = 0; row < 9; row++ ){
            Cell cell = grid[row][col];
            cell.removeFromPossibleContent(contentValue);
        }
    }

    private void resolvePossibilitiesInBox(int boxIndex, int contentValue){
        List<Cell> cells = sudoku.getBlock(boxIndex);
        for(Cell cell : cells){
            cell.removeFromPossibleContent(contentValue);
        }
    }

    public void printOutPossibilities(){
        for(int row = 0; row < 9; row++ ){
            for(int col = 0; col < 9; col++) {
                Cell cell = grid[row][col];
                List<Integer> possibilities = cell.getPossibleContent();
                if(!possibilities.isEmpty()){
                    System.out.println("Möglichkeiten in der Zelle " + row + ", " + col + ": " + possibilities);
                }
            }
        }
    }

    public void testForSinglePossibilitiesAndFillIn(){
        boolean nothingFound = true;
        for(int row = 0; row < 9; row++ ){
            for(int col = 0; col < 9; col++) {
                Cell cell = grid[row][col];
                if(cell.getPossibleContent().size() == 1){
                    nothingFound = false;
                    cell.content = cell.getPossibleContent().get(0);
                    log.info("Found " + cell.content + " in " + row + "," +col);
                }
            }
        }
        if(nothingFound){
            go1 = false;
            log.info("Found nothing to fill in");
        }
    }

    public void testForSinglePossibilitiesInContextAndFillIn(){
        boolean nothingFound = true;
        for(int row = 0; row < 9; row++ ){
            for(int col = 0; col < 9; col++) {
                Cell cell = grid[row][col];
                for(Integer i : cell.getPossibleContent() ){
                    int blockIndex = sudoku.getBlockIndexFromCell(row, col);
                    if( isSinglePossibilityInRow(row, cell, i) ||
                        isSinglePossibilityInColumn(col, cell, i) ||
                        isSinglePossibilityInBox(blockIndex, cell, i)
                    ){
                        cell.content = i;
                        nothingFound = false;
                    }
                }
            }
        }
        if(nothingFound){
            go2 = false;
            log.info("Found nothing to fill in");
        }
    }

    private boolean isSinglePossibilityInRow(int row, Cell selfCell, int possibilityValue){
        for(int col = 0; col < 9; col++) {
            Cell cell = grid[row][col];
            if (cell.getPossibleContent().contains(possibilityValue) && cell != selfCell) {
                return false;
            }
        }
        return true;
    }

    private boolean isSinglePossibilityInColumn(int col, Cell selfCell, int possibilityValue){
        for(int row = 0; row < 9; row++){
            Cell cell = grid[row][col];
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
            // hier stimmt was nicht
            if(cell.getPossibleContent().contains(possibilityValue)){
                cell.getPossibleContent().toString();
                return false;
            }
        }
        return true;
    }

}
