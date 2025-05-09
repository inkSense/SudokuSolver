package org.sudokusolver.A_entities.objectsAndDataStructures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Point;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BacktrackingSolver {

    private final DeterministicSolver engine = new DeterministicSolver();   // deine „Reasoning“-Klasse
    private static final Logger log = LoggerFactory.getLogger(BacktrackingSolver.class);

//    public boolean solve(SudokuBoard board) {
//
//        /* 1) Wurzel vorbereiten  */
//        SearchNode current = new SearchNode(board);
//        if (!propagateDeterministic(current)) return false;   // Widerspruch
//
//        /* 2) Iterativer Tiefensuch-Loop  */
//        while (true) {
//            if (current.getBoard().isSolved()) return true;
//
//            /* (a)  Ratepunkt bestimmen */
//            Point position = findMinimumOfPossibilitiesCellPosition(current.getBoard());
//            List<Integer> candidates = current.getBoard().validDigitsAt(position);
//            if (candidates.isEmpty()) {               // tot – darf nur vorkommen, wenn isValid() nicht greift
//                log.error("Hier sollten wir nie ankommen.");
//
//                current = backtrack(current);
//                if (current == null) return false;
//                continue;
//            }
//
//            /* (b)  erstes Rate-Kind bauen */
//            current = SearchNode.firstGuess(current, position, candidates);
//            SearchNode following  = current.nextChild();      // wähle 1. Wert
//            if (following == null) {              // kein Kind möglich
//                current = backtrack(current);
//                if (current == null) return false;
//                continue;
//            } else {
//                current = following;
//            }
//
//            /* (c) deterministisch weiter */
//            if (!propagateDeterministic(current)) {
//                current = backtrack(current);   // Widerspruch
//                if (current == null) return false; // Wurzel-Knoten
//            }
//        }
//    }

    public SudokuBoard solve(SudokuBoard board) {
        /* 1) Wurzel vorbereiten  */
        SearchNode current = new SearchNode(null, board);

        /* 2) Iterativer Tiefensuch-Loop  */
        while (true) {
            if (current.getBoard().isSolved()) {
                log.info("gelöst!");
                current.getBoard().print();
                return current.getBoard();
            }

            /* (a)  Ratepunkt bestimmen */
            Optional<Point> position = findCellPositionOfFewPossibilities(current.getBoard());
            if(position.isEmpty()){
                current.setHasValidChildren(false);
                current = current.getParent();
                continue;
            }
            List<Integer> candidates = current.getBoard().possiblesOfCellAt(position.get());
            if (candidates.isEmpty()) {
                log.error("Hier sollten wir nie ankommen.");
            }

            /* (b)  erstes Rate-Kind bauen */

            //current = SearchNode.copySearchNodeToChild(current, position, candidates);
            current = current.nextChild();

            SearchNode following  = current.nextChild();      // wähle 1. Wert


            if (following == null) {              // kein Kind möglich
                current = backtrackIter(current);
                if (current == null) return null;
                continue;
            } else {
                current = following;
            }

            /* (c) deterministisch weiter */
            engine.solveByReasoningAsFarAsPossible(current.getBoard());
        }
    }

    /* ---------- Hilfsmethoden ---------- */

//    /** führt deine Methoden aus; liefert false bei Widerspruch              */
//    private boolean propagateDeterministic(SearchNode node) {
//        SudokuBoard sudoku = (node.getBoard());
//        engine.solveByReasoningAsFarAsPossible(sudoku);
//        return node.getBoard().isValid();
//    }

    /** Cell mit den wenigsten >1 Kandidaten; 1 → würde sofort gesetzt      */
    private Optional<Point> findCellPositionOfFewPossibilities(SudokuBoard board) {
        return board.getCells().stream()
                .filter(c -> c.getContent() == 0)
                .filter(c -> !c.getPossibleContent().isEmpty())
                .min(Comparator.comparingInt(c -> c.getPossibleContent().size()))
                .map(Cell::getPosition);
    }

    private Cell findMinimumOfPossibilitiesCell(SudokuBoard board) {
        return board.getCells().stream()
                .filter(cell -> cell.getContent() == 0)
                .min(Comparator.comparingInt(cell -> cell.getPossibleContent().size()))
                .orElse(null);
    }


    /** springt so weit nach oben, bis ein Knoten noch Alternativen hat */
    private SearchNode backtrack(SearchNode node) {

        while (node != null && (node.exhausted() || !node.getBoard().isValid())) {
            node = node.getParent();       // eine Ebene hoch
        }
        if (node == null) return null;  // Wurzel erreicht → unlösbar
        SearchNode next = node.nextChild();           // nächste Alternative
        return next != null ? next : backtrack(node); // rekursiv, falls keine mehr
    }

    private SearchNode backtrackIter(SearchNode node) {
        int i = 0;
        while (true) {
            log.info(String.valueOf(i++));
            // 1. Hochlaufen, bis wir auf einer Ebene sind,
            //    die noch Kandidaten hat und gültig ist.
            while (node != null &&
                    (node.exhausted() || !node.getBoard().isValid())) {
                node = node.getParent();
            }

            // Wurzel erreicht? ⇒ unlösbar
            if (node == null) {
                return null;
            }

            // 2. Nächste Alternative auf dieser Ebene holen
            SearchNode next = node.nextChild();

            // Gefunden? – dann ausliefern
            if (next != null) {
                return next;
            }

            // 3. Keine Alternative mehr ⇒ weiter hoch laufen
            node = node.getParent();
            // (Schleife beginnt erneut – entspricht dem früheren
            //   rekursiven Aufruf `backtrack(node)`)
        }
    }
}

