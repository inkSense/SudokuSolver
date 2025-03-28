package org.sudokusolver.A_entities.objectsAndDataStructures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Sudoku {

    private static final Logger log = LoggerFactory.getLogger(Sudoku.class);
    List<Cell> cells = new ArrayList<Cell>();


    public Sudoku() {
        for(int i = 0; i < 81; i++){
            cells.add(new Cell());
        }
    }



    public List<Cell> getRow(int i) {
        List<Integer> indices = cellIndicesForRow(i);
        return indices.stream().map(cells::get).collect(Collectors.toList());
    }

    public List<Cell> getColumn(int i) {
        List<Integer> indices = cellIndicesForColumn(i);
        return indices.stream().map(cells::get).collect(Collectors.toList());
    }

    private List<Integer> cellIndicesForRow(int row){
        if(row < 0 || 8 < row){
            throw new IllegalArgumentException("Invalid row index: " + row);
        }
        int startIndex = row*9;
        int endIndex = startIndex + 8;
        return IntStream.rangeClosed(startIndex, endIndex).boxed().collect(Collectors.toList());
    }

    private List<Integer> cellIndicesForColumn(int column) {
        if (column < 0 || column > 8) {
            throw new IllegalArgumentException("Invalid column index: " + column);
        }
        return IntStream.range(0, 9)
                .map(i -> i * 9 + column)
                .boxed()
                .collect(Collectors.toList());
    }

    private List<Integer> cellIndicesForBlock(int blockNumber){
        //0 : Cell 0 1 2 , 9 10 11 , 18 19 20 ,
        //1 : Cell 3 4 5, 12 13 14 , 21 22 23 ,
        //2 : Cell 6 7 8, 15 16 17 , 24 25 26 ,

        if (blockNumber < 0 || blockNumber > 8) {
            throw new IllegalArgumentException("Invalid block number: " + blockNumber);
        }

        int blockRow = blockNumber / 3; // Zeilen-Position des Blocks (0, 1, 2)
        int blockCol = blockNumber % 3; // Spalten-Position des Blocks (0, 1, 2)

        int startRow = blockRow * 3;
        int startCol = blockCol * 3;

        List<Integer> indices = new ArrayList<>(9);
        for (int i = 0; i < 3; i++) {           // Zeilen im Block
            for (int j = 0; j < 3; j++) {       // Spalten im Block
                int row = startRow + i;
                int col = startCol + j;
                indices.add(row * 9 + col);
            }
        }
        return indices;
    }

    private int getBlockIndexFromCellIndex(int cellIndex) {
        if (cellIndex < 0 || cellIndex > 80) {
            throw new IllegalArgumentException("Invalid cell index: " + cellIndex);
        }

        int row = cellIndex / 9;
        int col = cellIndex % 9;

        int blockRow = row / 3;
        int blockCol = col / 3;

        return blockRow * 3 + blockCol;
    }


}
