package org.sudokusolver.A_entities.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.dataStructures.Position;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BacktrackingSolver {

    private final DeterministicSolver engine = new DeterministicSolver();
    private static final Logger log = LoggerFactory.getLogger(BacktrackingSolver.class);

    public SudokuBoard solve(SudokuBoard board) {
        SearchNode rootNode = new SearchNode(null, board);
        SearchNode current = rootNode.nextChild(); // erstes Kind
        int i = 0;
        while (true) {

            i++;

            current.getBoard().print();

            if (current.getBoard().isSolved()) {
                log.info("Durchläufe: " + i );
                return current.getBoard();
            }

            Optional<Position> position = findCellPositionOfFewPossibilities(current.getBoard());
            if(position.isEmpty()){
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
            current.getBoard().validate();
            if(current.getBoard().isValid()){
                current = current.nextChild();
            } else {
                SearchNode parent = current.getParent();
                if(parent == null) return null; // Wurzelknoten
                removeGuessOfChildInParent(current, parent);
                current = parent;
            }
        }
    }

    private SearchNode backtrack(SearchNode node) {
        // ToDo: Man kann backtracking schon vereinheitlichen!
        return null;
    }


    private void removeGuessOfChildInParent(SearchNode child, SearchNode parent){
        Cell parentCell = parent.getCell(child.getTriedPosition());
        parentCell.removeFromPossibleContent(child.getTriedValue());
    }

    private Optional<Position> findCellPositionOfFewPossibilities(SudokuBoard board) {
        return board.getCells().stream()
                .filter(c -> c.getContent() == 0)
                .filter(c -> !c.getPossibleContent().isEmpty())
                .min(Comparator.comparingInt(c -> c.getPossibleContent().size()))
                .map(Cell::getPosition);
    }

}

