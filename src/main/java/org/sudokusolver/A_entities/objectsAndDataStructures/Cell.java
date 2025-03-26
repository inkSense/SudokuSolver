
package org.sudokusolver.A_entities.objectsAndDataStructures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Cell {
    public int content;
    private List<Integer> possibleContent;

    public Cell() {
        this.content = 0;
        initializePossibleContent();
    }

    public boolean isFixed() {
        return content != 0;
    }

    public List<Integer> getPossibleContent() {
        return possibleContent;
    }

    public void removeFromPossibleContent(int number) {
        int index = possibleContent.indexOf(number);
        possibleContent.remove(index);
    }

    private void initializePossibleContent() {
        possibleContent = new ArrayList<>();
        IntStream.rangeClosed(1, 9).forEach(possibleContent::add);
    }


}
