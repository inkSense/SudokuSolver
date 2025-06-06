package org.sudokusolver.A_entities.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.dataStructures.Position;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SudokuBoard {

    private static final Logger log = LoggerFactory.getLogger(SudokuBoard.class);
    private final List<Cell> cells;
    private final String difficulty;
    private boolean valid;

    public SudokuBoard(List<Cell> cells, String difficulty) {
        this.cells = cells;
        this.difficulty = difficulty;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Cell getCell(Position position){
        for(Cell cell : cells){
            if(cell.getPosition().equals(position)){
                return cell;
            }
        }
        log.error("No Cell found in getCell()");
        return null;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void resetPossiblesInAllCells(){
        cells.forEach(Cell::initializePossibleContent);
    }

    public void validate() {
        // prüfe alle 9 Zeilen, Spalten und Blöcke
        valid = true;
        cells.forEach(c -> c.setValid(true));

        for (int i = 0; i < 9; i++) {
            // Bitwise OR:
            if ( !unitIsValid(getRow(i))            // Zeile
                    | !unitIsValid(getColumn(i))    // Spalte
                    | !unitIsValid(getBlock(i)) )   // 3×3‑Block
            {
                valid = false;
            }
        }
    }


    public void print() {
        System.out.println();
        for (int r = 0; r < 9; r++) {
            List<Cell> row = getRow(r);
            for (Cell cell : row) {
                System.out.print((cell.getContent() == 0 ? "." : cell.getContent()) + " ");
            }
            System.out.println();
        }
    }

    SudokuBoard copy() {
        List<Cell> cloned = cells.stream()
                .map(Cell::new)   // nutzt Copy-Konstruktor
                .collect(Collectors.toList());
        return new SudokuBoard(cloned, difficulty);
    }

    List<Integer> getPossiblesOfCellAt(Position position) {
        Cell cell = getCell(position);
        return new ArrayList<>(cell.getPossibleContent()); // Kopie zurückgeben
    }

    List<Cell> getRow(int row) {
        validateIndex(row);
        return cells.stream().filter(c->c.getPosition().row() == row).collect(Collectors.toList());
    }

    List<Cell> getColumn(int col) {
        validateIndex(col);
        return cells.stream().filter(c->c.getPosition().col() == col).collect(Collectors.toList());
    }

    List<Cell> getBlock(int blockNumber) {
        validateIndex(blockNumber);
        return cells.stream().filter(c->c.getBoxIndex() == blockNumber).collect(Collectors.toList());
    }

    boolean isValid(){
        return valid;
    }

    boolean isSolved(){
        if(!valid) return false;
        for(Cell cell: cells){
            if(cell.getContent() == 0){
                return false;
            }
        }
        return true;
    }

    static void validateIndex(int index) {
        if (index < 0 || index > 8) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
    }

    /** Liefert false, sobald ein Wert < 1 oder > 9 vorkommt oder
     *  eine Zahl (1‑9) doppelt vorkommt. 0 (=leer) wird ignoriert. */
    private boolean unitIsValid(List<Cell> unit) {
        boolean[] seen = new boolean[10];          // Index 1‑9
        for (Cell cell : unit) {
            int value = cell.getContent();
            if (value == 0)            continue;       // leere Zelle
            if (value < 1 || value > 9)    return false;   // ungültiger Wert
            if (seen[value]){   // doppelte Zahl
                markCellsWithSameValueNotValid(unit, value);
                return false;
            }
            seen[value] = true;
        }
        return true;
    }

    private void markCellsWithSameValueNotValid(List<Cell> cells, int value){
        List<Cell> sameContent = cells.stream().filter(c->c.getContent() == value).toList();
        for(Cell cell : sameContent){
            cell.setValid(false);
        }
    }

    private void markCellsValid(List<Cell> cells){
        for(Cell cell : cells){
            cell.setValid(true);
        }
    }



}
