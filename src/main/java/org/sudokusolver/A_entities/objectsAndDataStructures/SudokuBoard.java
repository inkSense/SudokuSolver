package org.sudokusolver.A_entities.objectsAndDataStructures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        return cells.stream().filter(c->c.position.y == row).toList();
    }

    public List<Cell> getColumn(int col) {
        validateIndex(col);
        return cells.stream().filter(c->c.position.x == col).toList();
    }

    public List<Cell> getBlock(int blockNumber) {
        validateIndex(blockNumber);
        return cells.stream().filter(c->c.boxIndex == blockNumber).toList();
    }

    static void validateIndex(int index) {
        if (index < 0 || index > 8) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
    }
}
