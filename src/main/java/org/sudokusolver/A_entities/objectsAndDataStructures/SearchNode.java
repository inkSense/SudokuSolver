package org.sudokusolver.A_entities.objectsAndDataStructures;

import java.util.List;

public class SearchNode {

    private final SudokuBoard board;        // aktueller Zustand
    private final SearchNode   parent;      // ↑  null = Wurzel
    private record Trial(Position point, Integer value){};
    private Trial tried;

    public SearchNode(SearchNode parent, SudokuBoard rootBoard) {
        this.board        = rootBoard;
        this.parent       = parent;
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public List<Integer> getPossiblesOfCellAt(Position position){
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

    public Position getTriedPosition(){
        return tried.point;
    }

    private void setTried(Position position, int value) {
        this.tried = new Trial(position, value);
    }

    public void setTriedAndSetTriedValueToCellAt(Position position, int value){
        getCell(position).setContent(value);
        setTried(position, value);
    }

    public Cell getCell(Position position){
        return board.getCell(position);
    }

    public SearchNode nextChild() {
        SudokuBoard boardCopy = board.copy();
        return new SearchNode(this, boardCopy);
    }

}
