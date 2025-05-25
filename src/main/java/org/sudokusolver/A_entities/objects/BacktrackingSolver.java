package org.sudokusolver.A_entities.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.dataStructures.Position;

import java.util.Comparator;
import java.util.Optional;

public class BacktrackingSolver {

    private final DeterministicSolver engine = new DeterministicSolver();
    private static final Logger log = LoggerFactory.getLogger(BacktrackingSolver.class);

    public SudokuBoard solve(SudokuBoard board) {
        SearchNode current = new SearchNode(null, board);
        int loops = 0;

        while (current != null) {
            loops++;
            //current.getBoard().print();

            if (!makeGuessIfPossible(current)) {
                current = backtrack(current);
                continue;
            }

            // clear
            engine.solveByReasoningAsFarAsPossible(current.getBoard());
            current.getBoard().validate();

            if (current.getBoard().isValid()) {
                if (current.getBoard().isSolved()) {
                    log.info("Durchläufe: {}", loops);
                    return current.getBoard();
                }
                current = current.nextChild();
            } else {
                current = backtrack(current);
            }
        }
        return null; // Wurzelknoten
    }

    private SearchNode backtrack(SearchNode node) {
        // Den Versuch entweder im Vater oder im Großvater streichen
        SearchNode child  = (node.getTried() == null) ? node.getParent() : node;
        if (child == null) return null; // Wurzelknoten
        SearchNode parent = child.getParent();
        if (parent == null) return null; // Wurzelknoten
        removeGuessOfChildInParent(child, parent);
        return parent;
    }

    private boolean makeGuessIfPossible(SearchNode node) {
        Optional<Position> posOpt = findCellPositionOfFewPossibilities(node.getBoard());
        if (posOpt.isEmpty()) return false;
        Position pos   = posOpt.get();
        int value      = node.getPossiblesOfCellAt(pos).get(0);
        node.setTriedAndSetTriedValueToCellAt(pos, value);
        return true;
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