package org.sudokusolver.A_entities.objects;

import org.sudokusolver.A_entities.dataStructures.Position;

import java.util.List;

public class SearchNode {

    private final SudokuBoard board;        // aktueller Zustand
    private final SearchNode   parent;      // ↑  null = Wurzel
    private record Trial(Position point, Integer value){}
    private Trial tried;

    public SearchNode(SearchNode parent, SudokuBoard rootBoard) {
        this.board        = rootBoard;
        this.parent       = parent;
    }

    SudokuBoard getBoard() {
        return board;
    }

    List<Integer> getPossiblesOfCellAt(Position position){
        return board.getPossiblesOfCellAt(position);
    }

    SearchNode getParent() {
        return parent;
    }

    int getTriedValue() {
        return tried.value;
    }

    Trial getTried() {
        return tried;
    }

    Position getTriedPosition(){
        return tried.point;
    }

    void setTriedAndSetTriedValueToCellAt(Position position, int value){
        getCell(position).setContent(value);
        setTried(position, value);
    }

    Cell getCell(Position position){
        return board.getCell(position);
    }

    SearchNode nextChild() {
        SudokuBoard boardCopy = board.copy();
        return new SearchNode(this, boardCopy);
    }

    private void setTried(Position position, int value) {
        this.tried = new Trial(position, value);
    }



}
