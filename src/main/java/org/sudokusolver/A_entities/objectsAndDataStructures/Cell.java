
package org.sudokusolver.A_entities.objectsAndDataStructures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.awt.Point;

public class Cell {
    public int content;
    public Point position;
    public int boxIndex;
    public List<Integer> possibleContent;



    public Cell() {
        this.content = 0;
        initializePossibleContent();
    }

    public Cell(int content, int row, int col) {
        this.content = content;
        this.position = new Point(col, row);
        this.boxIndex = getBlockIndexFromCell(row, col);
        initializePossibleContent();
    }

    public void removeFromPossibleContent(int number) {
        int index = possibleContent.indexOf(number);
        if(index != -1) {
            possibleContent.remove(index);
        }
    }

    public void removeAllPossibilities(){
        possibleContent.clear();
    }

    private void initializePossibleContent() {
        possibleContent = new ArrayList<>();
        IntStream.rangeClosed(1, 9).forEach(possibleContent::add);
    }

    int getBlockIndexFromCell(int row, int col) {
        SudokuBoard.validateIndex(row);
        SudokuBoard.validateIndex(col);
        int blockRow = row / 3;
        int blockCol = col / 3;
        return blockRow * 3 + blockCol;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "content=" + content +
                '}';
    }
}
