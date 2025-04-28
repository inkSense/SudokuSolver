package org.sudokusolver.A_entities.objectsAndDataStructures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.awt.Point;

public class SolvingSudokus {
    SudokuBoard sudoku;
    private static final Logger log = LoggerFactory.getLogger(SolvingSudokus.class);

    private Tree<SudokuBoard> searchTree;

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



    public void solveByReasoningAsFarAsPossible(){
        boolean singleFound;
        boolean contextFound;
        for(int i = 0; i < 80; i++){
            singleFound = testForSinglePossibilitiesAndFillIn();
            reducePossibilitiesFromCurrentState();
            contextFound = testForSinglePossibilitiesInContextAndFillIn();
            reducePossibilitiesFromCurrentState();
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
            if(cell.possibleContent.size() == 1){
                somethingFound = true;
                cell.content = cell.possibleContent.get(0);
                cell.removeAllPossibilities();
                //log.info("Found " + cell.content + " in " + cell.position.y + "," + cell.position.x);
            }
        }
        return somethingFound;
    }

    public boolean testForSinglePossibilitiesInContextAndFillIn(){
        boolean somethingFound = false;
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
                        somethingFound = true;
                        reducePossibilitiesFromCurrentState();
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
            if (cell.possibleContent.contains(possibilityValue) && cell != selfCell) {
                return false;
            }
        }
        return true;
    }

    private boolean isSinglePossibilityInColumn(int col, Cell selfCell, int possibilityValue){
        List<Cell> colWithoutItself = sudoku.getColumn(col);
        colWithoutItself.remove(selfCell);
        for(Cell cell : colWithoutItself){
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

    // einfach eingefügt. Muss los.

    // TODO: Check if right

  // ← Wrapper

    /* ***** öffentliche API ***** */

    /** löst das Sudoku; gibt true zurück, wenn gelöst */
    public SudokuBoard solve() {
        searchTree = new Tree<>(sudoku.copy());          // Wurzel
        boolean solved = backtrack(searchTree.root());
        if (solved) sudoku = searchTree.root().getValue(); // Ergebnis zurückschreiben
        return sudoku;
    }

    /** optional: für Statistiken / Debug */
    public long examinedLeaves() { return searchTree.leafCount(); }

    /* ***** Backtracking-Kernel ***** */

    private boolean backtrack(TreeNode<SudokuBoard> node) {
        SudokuBoard board = node.getValue();

        /* 1. deterministische Logik ausschöpfen */
        SolvingSudokus helper = new SolvingSudokus(); // temporäre “Engine”
        helper.setSudoku(board);
        helper.reducePossibilitiesFromCurrentState();
        helper.solveByReasoningAsFarAsPossible();

        /* 2. Konsistenz prüfen */
        if (!board.isValid())  return false;  // Widerspruch
        if (board.isSolved())  return true;   // Ziel erreicht

        /* 3. Verzweigen: erste leere Zelle + Kandidaten */
        Point p = board.nextEmptyCell();
        for (int d : board.validDigitsAt(p)) {
//            SudokuBoard copy = board.copy();
//            copy.getCell(p.x, p.y).setContent(d);
//
//            TreeNode<SudokuBoard> child = node.addChild(copy);
//            if (backtrack(child)) return true;         // Erfolg propagieren
            SudokuBoard copy = board.copy();
            Cell target = copy.getCell(p.x, p.y);
            target.setContent(d);
            target.removeAllPossibilities();   // Zelle ist jetzt fest

            // Kandidaten im Kind-Board aktualisieren
            SolvingSudokus tmp = new SolvingSudokus();
            tmp.setSudoku(copy);
            tmp.reducePossibilitiesFromCurrentState();

            TreeNode<SudokuBoard> child = node.addChild(copy);
            if (backtrack(child)) return true;

        }
        return false;                                  // alle Kandidaten scheitern → Backtrack
    }




}
