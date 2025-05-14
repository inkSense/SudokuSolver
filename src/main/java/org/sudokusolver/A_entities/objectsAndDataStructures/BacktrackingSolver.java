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
        int i = 0;
        while (true) {

            //current.getBoard().print();

            if(++i % 2 == 0){
                log.info("Durchlauf " + i );
            }



            if (current.getBoard().isSolved()) {
                return current.getBoard();
            }

            Optional<Point> position = findCellPositionOfFewPossibilities(current.getBoard());
            if(position.isEmpty()){
                //log.info("Keine Kandidaten fürs Raten. Enferne Ratemöglichkeit im Vater.");
                SearchNode parent = current.getParent();
                if(parent == null) return null; // Wurzelknoten
                SearchNode grandParent = parent.getParent();
                if(grandParent == null) return null; // Wurzelknoten
                if(current.getTried() == null) {
                    removeGuessOfChildInParent(parent, grandParent);
                    current = grandParent;
                } else {
                    removeGuessOfChildInParent(current, parent);
                    current = parent;
                }
                continue;
            }
            // Es gibt mögliche Kindpositionen.
            List<Integer> candidates = current.getPossiblesOfCellAt(position.get());
            int tryValue = candidates.get(0); // Hier wird geraten
            current.setTriedAndSetTriedValueToCellAt(position.get(), tryValue);
            engine.solveByReasoningAsFarAsPossible(current.getBoard());
//            log.info("Versuch: " + tryValue + " auf " + position.get());

            if(current.getBoard().isValid()){
//                log.info("Das ist Valide. Nächstes Kind.");
                current = current.nextChild();
            } else {
//                log.info("Das ist UNvalide. Enferne Ratemöglichkeit im Vater.");
                SearchNode parent = current.getParent();
                if(parent == null) return null; // Wurzelknoten
                removeGuessOfChildInParent(current, parent);
                current = parent;
            }

            /* (c) deterministisch weiter */
            //engine.solveByReasoningAsFarAsPossible(current.getBoard());
        }
    }

    private void removeGuessOfChildInParent(SearchNode child, SearchNode parent){
        Cell parentCell = parent.getCell(child.getTriedPosition());
        parentCell.removeFromPossibleContent(child.getTriedValue());
    }

    private void removeGuess(SearchNode node){
        Cell cell = node.getCell(node.getTriedPosition());
        cell.setContent(0);
        cell.removeFromPossibleContent(node.getTriedValue());
    }

    private Optional<Point> findCellPositionOfFewPossibilities(SudokuBoard board) {
        return board.getCells().stream()
                .filter(c -> c.getContent() == 0)
                .filter(c -> !c.getPossibleContent().isEmpty())
                .min(Comparator.comparingInt(c -> c.getPossibleContent().size()))
                .map(Cell::getPosition);
    }

}

