package org.sudokusolver.A_entities.objectsAndDataStructures;


import java.awt.Point;
import java.util.List;

public class SearchNode {

    private final SudokuBoard board;        // aktueller Zustand
    private final SearchNode   parent;      // ↑  null = Wurzel
    private record Trial(Point point, Integer value){};
    private Trial tried;

    public SearchNode(SearchNode parent, SudokuBoard rootBoard) {
        this.board        = rootBoard;
        this.parent       = parent;
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public List<Integer> getPossiblesOfCellAt(Point position){
        return board.getPossiblesOfCellAt(position);
    }

    public SearchNode getParent() {
        return parent;
    }

    public int getTriedValue() {
        return tried.value;
    }

    public Trial getTried() {
        return tried;
    }

    public Point getTriedPosition(){
        return tried.point;
    }

    private void setTried(Point position, int value) {
        this.tried = new Trial(position, value);
    }

    public void setTriedAndSetTriedValueToCellAt(Point position, int value){
        getCell(position).setContent(value);
        setTried(position, value);
    }

    public Cell getCell(Point position){
        return board.getCell(position);
    }

    public SearchNode nextChild() {
        SudokuBoard boardCopy = board.copy();
        return new SearchNode(this, boardCopy);
    }

}
