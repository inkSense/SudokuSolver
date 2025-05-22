
package org.sudokusolver.A_entities.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.dataStructures.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Cell {
    private int content;
    private Position position;
    private int boxIndex;
    private List<Integer> possibleContent;
    private boolean valid = true;
    Logger log = LoggerFactory.getLogger(Cell.class);

    public Cell() {
        this.content = 0;
        initializePossibleContent();
    }

    public Cell(int content, Position position) {
        this.content = content;
        this.position = position;
        this.boxIndex = getBlockIndexFromCell(position);
        initializePossibleContent();
    }

    /** Copy-Konstruktor */
    public Cell(Cell src) {
        this.content  = src.content;
        this.position = src.position;
        this.boxIndex = src.boxIndex;
        this.possibleContent = new ArrayList<>(src.possibleContent);
        this.valid = src.valid; // Braucht man das?
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public Position getPosition() {
        return position;
    }

    public int getBoxIndex() {
        return boxIndex;
    }

    public List<Integer> getPossibleContent() {
        return possibleContent;
    }

    public void setPossibleContent(List<Integer> possibleContent) {
        this.possibleContent = possibleContent;
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

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "content=" + content +
                '}';
    }

    void initializePossibleContent() {
        List<Integer> possibles = new ArrayList<>();
        if(content == 0) {
            IntStream.rangeClosed(1, 9).forEach(possibles::add);
        }
        possibleContent = possibles;
    }

    private int getBlockIndexFromCell(Position position) {
        int row = position.row();
        int col = position.col();
        SudokuBoard.validateIndex(row);
        SudokuBoard.validateIndex(col);
        int blockRow = row / 3;
        int blockCol = col / 3;
        return blockRow * 3 + blockCol;
    }
}
