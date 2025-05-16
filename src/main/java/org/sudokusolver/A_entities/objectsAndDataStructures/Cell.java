
package org.sudokusolver.A_entities.objectsAndDataStructures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.awt.Point;

public class Cell {
    private int content;
    private Point position;
    private int boxIndex;
    private List<Integer> possibleContent;
    private boolean valid = true;
    Logger log = LoggerFactory.getLogger(Cell.class);

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

    /** Copy-Konstruktor */
    public Cell(Cell src) {
        this.content  = src.content;
        this.position = new Point(src.position); // tiefe Kopie
        this.boxIndex = src.boxIndex;
        this.possibleContent = new ArrayList<>(src.possibleContent);
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
        // ToDo: Bitte testen, ob man das für den Deterministischen Löser ausgeschalten darf:
        //initializePossibleContent();
    }

    public Point getPosition() {
        return position;
    }

    public int getBoxIndex() {
        return boxIndex;
    }

    public List<Integer> getPossibleContent() {
        return possibleContent;
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

    public void initializePossibleContent() {
        List<Integer> possibles = new ArrayList<>();
        if(content == 0) {
            IntStream.rangeClosed(1, 9).forEach(possibles::add);
        }
        possibleContent = possibles;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
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
