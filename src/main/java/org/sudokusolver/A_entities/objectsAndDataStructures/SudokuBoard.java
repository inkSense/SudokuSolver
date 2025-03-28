package org.sudokusolver.A_entities.objectsAndDataStructures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuBoard {

    private static final Logger log = LoggerFactory.getLogger(SudokuBoard.class);
    private final Cell[][] cells;

    public SudokuBoard(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void print() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                System.out.print((cell.content == 0 ? "." : cell.content) + " ");
            }
            System.out.println();
        }
    }

    public List<Cell> getRow(int row) {
        validateIndex(row);
        List<Cell> result = new ArrayList<>();
        for (int col = 0; col < 9; col++) {
            result.add(cells[row][col]);
        }
        return result;
    }

    public List<Cell> getColumn(int col) {
        validateIndex(col);
        List<Cell> result = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            result.add(cells[row][col]);
        }
        return result;
    }

    public List<Cell> getBlock(int blockNumber) {
        validateIndex(blockNumber);
        int startRow = (blockNumber / 3) * 3;
        int startCol = (blockNumber % 3) * 3;
        List<Cell> result = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result.add(cells[startRow + i][startCol + j]);
            }
        }
        return result;
    }

    public int getBlockIndexFromCell(int row, int col) {
        validateIndex(row);
        validateIndex(col);
        int blockRow = row / 3;
        int blockCol = col / 3;
        return blockRow * 3 + blockCol;
    }

    private void validateIndex(int index) {
        if (index < 0 || index > 8) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
    }
}
