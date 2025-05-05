package org.sudokusolver.A_entities.objectsAndDataStructures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Point;
import java.util.Comparator;
import java.util.List;

public class BacktrackingSolver {

    private final SolvingSudokus engine = new SolvingSudokus();   // deine „Reasoning“-Klasse
    private static final Logger log = LoggerFactory.getLogger(BacktrackingSolver.class);

    public boolean solve(SudokuBoard board) {

        /* 1) Wurzel vorbereiten  */
        SearchNode current = new SearchNode(board);
        if (!propagateDeterministic(current)) return false;   // Widerspruch

        /* 2) Iterativer Tiefensuch-Loop  */
        while (true) {
            if (current.getBoard().isSolved()) return true;        // ✓

            /* (a)  Ratepunkt bestimmen */
            Point p = findMinimumOfPossibilitiesCell(current.getBoard());
            List<Integer> candidates = current.getBoard().validDigitsAt(p);
            if (candidates.isEmpty()) {               // tot – darf nur vorkommen, wenn isValid() nicht greift
                log.error("Hier sollten wir nie ankommen.");

                current = backtrack(current);
                if (current == null) return false;
                continue;
            }

            /* (b)  erstes Rate-Kind bauen */
            current = SearchNode.firstGuess(current, p, candidates);
            SearchNode following  = current.nextChild();      // wähle 1. Wert
            if (following == null) {              // kein Kind möglich
                current = backtrack(current);
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
        SudokuBoard sudoku = (node.getBoard());
        engine.solveByReasoningAsFarAsPossible(sudoku);
        return node.getBoard().isValid();
    }

    /** Cell mit den wenigsten >1 Kandidaten; 1 → würde sofort gesetzt      */
    private Point findMinimumOfPossibilitiesCell(SudokuBoard board) {
        return board.getCells().stream()
                .filter(c -> c.getContent() == 0)
                .min(Comparator.comparingInt(c -> c.getPossibleContent().size()))
                .orElseThrow().getPosition();
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

