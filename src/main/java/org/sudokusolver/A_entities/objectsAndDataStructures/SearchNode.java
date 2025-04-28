package org.sudokusolver.A_entities.objectsAndDataStructures;


import java.awt.Point;
import java.util.List;

public class SearchNode {

    private final SudokuBoard board;        // aktueller Zustand
    private final SearchNode   parent;      // ↑  null = Wurzel
    private final Point decisionPos; // Zelle, an der wir raten
    private final List<Integer> remaining;  // Kandidaten, die noch nicht versucht wurden
    private final int          triedValue;  // -1  ==   noch nichts versucht

    /* Wurzel-Konstruktor */
    public SearchNode(SudokuBoard rootBoard) {
        this.board        = rootBoard;
        this.parent       = null;
        this.decisionPos  = null;
        this.remaining    = List.of();      // keine Kandidaten
        this.triedValue   = -1;
    }

    /* Kind-Knoten nach einem Versuch */
    private SearchNode(SearchNode parent, Point pos, List<Integer> remaining, int value, SudokuBoard board) {
        this.parent      = parent;
        this.decisionPos = pos;
        this.remaining   = remaining;
        this.triedValue  = value;
        this.board       = board;
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public SearchNode getParent() {
        return parent;
    }

    public Point getDecisionPos() {
        return decisionPos;
    }

    public List<Integer> getRemaining() {
        return remaining;
    }

    public int getTriedValue() {
        return triedValue;
    }

    /** baut das nächste Kind – oder null, wenn alle Kandidaten aufgebraucht */
    public SearchNode nextChild() {
        if (remaining.isEmpty()) return null;
        int value      = remaining.get(0);
        List<Integer> rest = remaining.subList(1, remaining.size());

        SudokuBoard copy = board.copy();
        copy.getCell(decisionPos.x, decisionPos.y).setContent(value);

        return new SearchNode(this, decisionPos, rest, value, copy);
    }

    /** true, wenn dieser Knoten keine gültigen Kinder mehr hat → Backtrack */
    public boolean exhausted() {
        return remaining.isEmpty();
    }

    /* Fabrikmethode für das erste Rate-Kind */
    public static SearchNode firstGuess(SearchNode parent, Point pos, List<Integer> candidates) {
        return new SearchNode(parent, pos, candidates, -1, parent.board.copy());
    }
}
