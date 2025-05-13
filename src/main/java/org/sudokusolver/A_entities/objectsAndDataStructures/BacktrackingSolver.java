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

    public SudokuBoard solve(SudokuBoard board) {
        SearchNode rootNode = new SearchNode(null, board);
        SearchNode current = rootNode.nextChild(); // erstes Kind

        while (true) {
            current.getBoard().print();

            if (current.getBoard().isSolved()) {
                return current.getBoard();
            }

            Optional<Point> position = findCellPositionOfFewPossibilities(current.getBoard());
            if(position.isEmpty()){
                SearchNode parent = current.getParent();
                if(parent == null) return null; // Wurzelknoten
                if(current.getTried() != null) {
                    removeGuessOfChildInParent(current, parent);
                    current = parent;
                } else {
                    SearchNode grandParent = parent.getParent();
                    if(grandParent == null) return null; // Wurzelknoten
                    removeGuessOfChildInParent(parent, grandParent);
                    current = grandParent;
                }

                continue;
            }
            // Es gibt mögliche Kindpositionen.
            List<Integer> candidates = current.getPossiblesOfCellAt(position.get());
            int tryValue = candidates.get(0); // Hier wird geraten
            current.setTriedAndSetTriedValueToCellAt(position.get(), tryValue);
            //log.info("Versuch: " + tryValue + " auf " + position.get());

            if(current.getBoard().isValid()){
                current = current.nextChild();
            } else {
                SearchNode parent = current.getParent();
                if(parent == null) return null; // Wurzelknoten
                if(current.getTried() != null) {
                    removeGuessOfChildInParent(current, parent);
                    current = parent;
                } else {
                    SearchNode grandParent = parent.getParent();
                    if(grandParent == null) return null; // Wurzelknoten
                    removeGuessOfChildInParent(parent, grandParent);
                    current = grandParent;
                }
            }

            /* (c) deterministisch weiter */
            //engine.solveByReasoningAsFarAsPossible(current.getBoard());
        }
    }

    private void removeGuessOfChildInParent(SearchNode child, SearchNode parent){
        Cell parentCell = parent.getCell(child.getTriedPosition());
        parentCell.setContent(0);
        parentCell.removeFromPossibleContent(child.getTriedValue());
        engine.reducePossibilitiesFromCurrentState(parent.getBoard());
    }

    private Optional<Point> findCellPositionOfFewPossibilities(SudokuBoard board) {
        return board.getCells().stream()
                .filter(c -> c.getContent() == 0)
                .filter(c -> !c.getPossibleContent().isEmpty())
                .min(Comparator.comparingInt(c -> c.getPossibleContent().size()))
                .map(Cell::getPosition);
    }

}

