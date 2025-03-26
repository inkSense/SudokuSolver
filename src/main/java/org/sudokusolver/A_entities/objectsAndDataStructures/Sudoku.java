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
        return null;
    }


    private List<Integer> cellIndicesForRow(int i){
        if(i < 0 || 8 < i){
            log.error("Dont use this row index: " + i );
        }
        int startIndex = i*9;
        int endIndex = startIndex + 8;
        return IntStream.rangeClosed(startIndex, endIndex).boxed().collect(Collectors.toList());
    }

    private List<Integer> cellIndicesForColumn(int i){
        if(i < 0 || 8 < i){
            log.error("Dont use this column index: " + i );
        }
        return null;
    }
}
