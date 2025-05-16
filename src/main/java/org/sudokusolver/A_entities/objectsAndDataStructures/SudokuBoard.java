package org.sudokusolver.A_entities.objectsAndDataStructures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SudokuBoard {

    private static final Logger log = LoggerFactory.getLogger(SudokuBoard.class);
    private final List<Cell> cells;
    private final String difficulty;

    public SudokuBoard(List<Cell> cells, String difficulty) {
        this.cells = cells;
        this.difficulty = difficulty;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Cell getCell(Point position){
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

    public List<Cell> getRow(int row) {
        validateIndex(row);
        return cells.stream().filter(c->c.getPosition().y == row).collect(Collectors.toList());
    }

    public List<Cell> getColumn(int col) {
        validateIndex(col);
        return cells.stream().filter(c->c.getPosition().x == col).collect(Collectors.toList());
    }

    public List<Cell> getBlock(int blockNumber) {
        validateIndex(blockNumber);
        return cells.stream().filter(c->c.getBoxIndex() == blockNumber).collect(Collectors.toList());
    }

    public boolean isValid() {

        // prüfe alle 9 Zeilen, Spalten und Blöcke
        for (int i = 0; i < 9; i++) {
            if (unitNotValid(getRow(i)) ||   // Zeile
                    unitNotValid(getColumn(i)) ||   // Spalte
                    unitNotValid(getBlock(i)))        // 3×3‑Block
            {
                return false;              // Widerspruch gefunden
            }
        }
        return true;                       // kein Konflikt
    }

    public boolean isSolved(){
        for(Cell cell: cells){
            if(cell.getContent() == 0){
                return false;
            }
        }
        return true;
    }

    public SudokuBoard copy() {
        List<Cell> cloned = cells.stream()
                .map(Cell::new)   // nutzt Copy-Konstruktor
                .collect(Collectors.toList());
        return new SudokuBoard(cloned, difficulty);
    }

    public List<Integer> getPossiblesOfCellAt(Point position) {
        Cell cell = getCell(position);
        return new ArrayList<>(cell.getPossibleContent()); // Kopie zurückgeben
    }

    static void validateIndex(int index) {
        if (index < 0 || index > 8) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
    }

    /** Liefert false, sobald ein Wert < 1 oder > 9 vorkommt oder
     *  eine Zahl (1‑9) doppelt vorkommt. 0 (=leer) wird ignoriert. */
    private static boolean unitNotValid(List<Cell> unit) {
        boolean[] seen = new boolean[10];          // Index 1‑9
        for (Cell cell : unit) {
            int value = cell.getContent();
            if (value == 0)            continue;       // leere Zelle
            if (value < 1 || value > 9)    return true;   // ungültiger Wert
            if (seen[value]){// doppelte Zahl
                markCellsWithValueNotValid(unit, value);
                return true;
            }
            seen[value] = true;
        }
        return false;
    }

    private static void markCellsWithValueNotValid(List<Cell> cells, int value){
        List<Cell> sameContent = cells.stream().filter(c->c.getContent() == value).toList();
        for(Cell cell : sameContent){
            cell.setValid(false);
        }
    }

    public void resetPossiblesInAllCells(){
        cells.forEach(Cell::initializePossibleContent);
    }



}
