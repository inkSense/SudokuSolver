package org.sudokusolver.A_entities.objectsAndDataStructures;

import java.awt.Point;
import java.util.Comparator;
import java.util.List;

public class BacktrackingSolver {

    private final SolvingSudokus engine = new SolvingSudokus();   // deine „Reasoning“-Klasse

    public boolean solve(SudokuBoard board) {

        /* 1) Wurzel vorbereiten  */
        SearchNode current = new SearchNode(board);
        if (!propagateDeterministic(current)) return false;   // Widerspruch

        /* 2) Iterativer Tiefensuch-Loop  */
        while (true) {
            if (current.getBoard().isSolved()) return true;        // ✓

            /* (a)  Ratepunkt bestimmen */
            Point p = findMinimumOfPossibilitiesCell(current.getBoard());
            List<Integer> cand = current.getBoard().validDigitsAt(p);
            if (cand.isEmpty()) {               // tot – darf nur vorkommen, wenn isValid() nicht greift
                current = backtrack(current);   //  ↩
                if (current == null) return false;
                continue;
            }

            /* (b)  erstes Rate-Kind bauen */
            current = SearchNode.firstGuess(current, p, cand);
            current = current.nextChild();      // wähle 1. Wert
            if (current == null) {              // kein Kind möglich
                current = backtrack(current);   //  ↩
                if (current == null) return false;
                continue;
            }

            /* (c) deterministisch weiter */
            if (!propagateDeterministic(current)) {
                current = backtrack(current);   // Widerspruch ↩
                if (current == null) return false;
            }
        }
    }

    /* ---------- Hilfsmethoden ---------- */

    /** führt deine Methoden aus; liefert false bei Widerspruch              */
    private boolean propagateDeterministic(SearchNode node) {
        engine.setSudoku(node.getBoard());
        engine.reducePossibilitiesFromCurrentState();
        engine.solveByReasoningAsFarAsPossible();
        return node.getBoard().isValid();
    }

    /** Cell mit den wenigsten >1 Kandidaten; 1 → würde sofort gesetzt      */
    private Point findMinimumOfPossibilitiesCell(SudokuBoard board) {
        return board.getCells().stream()
                .filter(c -> c.content == 0)
                .min(Comparator.comparingInt(c -> c.possibleContent.size()))
                .orElseThrow().position;
    }

    /** springt so weit nach oben, bis ein Knoten noch Alternativen hat */
    private SearchNode backtrack(SearchNode node) {
        while (node != null && (node.exhausted() || !node.getBoard().isValid())) {
            node = node.getParent();       // eine Ebene hoch
        }
        if (node == null) return null;                // Wurzel erreicht → unlösbar
        SearchNode next = node.nextChild();           // nächste Alternative
        return next != null ? next : backtrack(node); // rekursiv, falls keine mehr
    }
}

