package org.sudokusolver.A_entities.objectsAndDataStructures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;
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

    public Cell getCell(int col, int row){
        for(Cell cell : cells ){
            if(cell.position.x == col && cell.position.y == row){
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
        for (int r = 0; r < 9; r++) {
            List<Cell> row = getRow(r);
            for (Cell cell : row) {
                System.out.print((cell.content == 0 ? "." : cell.content) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public List<Cell> getRow(int row) {
        validateIndex(row);
        return cells.stream().filter(c->c.position.y == row).collect(Collectors.toList());
    }

    public List<Cell> getColumn(int col) {
        validateIndex(col);
        return cells.stream().filter(c->c.position.x == col).collect(Collectors.toList());
    }

    public List<Cell> getBlock(int blockNumber) {
        validateIndex(blockNumber);
        return cells.stream().filter(c->c.boxIndex == blockNumber).collect(Collectors.toList());
    }

    public boolean isValid() {

        // prüfe alle 9 Zeilen, Spalten und Blöcke
        for (int i = 0; i < 9; i++) {
            if (!unitValid(getRow(i))      ||   // Zeile
                    !unitValid(getColumn(i))   ||   // Spalte
                    !unitValid(getBlock(i)))        // 3×3‑Block
            {
                return false;              // Widerspruch gefunden
            }
        }
        return true;                       // kein Konflikt
    }

    public boolean isSolved(){
        for(Cell cell: cells){
            if(cell.content == 0){
                return false;
            }
        }
        return true;
    }

    SudokuBoard copy(){
        return new SudokuBoard(getCells(), getDifficulty());
    }

    static void validateIndex(int index) {
        if (index < 0 || index > 8) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
    }

    /** Liefert false, sobald ein Wert <1 oder >9 vorkommt oder
     *  eine Zahl (1‑9) doppelt vorkommt. 0 (=leer) wird ignoriert. */
    private static boolean unitValid(List<Cell> unit) {
        boolean[] seen = new boolean[10];          // Index 1‑9
        for (Cell c : unit) {
            int v = c.content;
            if (v == 0)            continue;       // leere Zelle
            if (v < 1 || v > 9)    return false;   // ungültiger Wert
            if (seen[v])           return false;   // doppelte Zahl
            seen[v] = true;
        }
        return true;
    }



}
