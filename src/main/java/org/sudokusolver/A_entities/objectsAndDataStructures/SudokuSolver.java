package org.sudokusolver.A_entities.objectsAndDataStructures;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Utility class that solves a Sudoku via depth‑first search with backtracking.
 * The recursion depth corresponds to a path in the implicit search tree.
 */
public final class SudokuSolver {

    /** Node representing a board state in the search tree. */
    private static final class Node {
        final SudokuBoard sudoku;      // current board state
        final Node         parent;    // back‑link for optional tracing
        final String       lastMove;  // e.g. "R4C6 = 7" (for debugging)

        Node(SudokuBoard sudoku, Node parent, String lastMove) {
            this.sudoku = sudoku;
            this.parent   = parent;
            this.lastMove = lastMove;
        }
    }

    /** Immutable coordinate pair (row, column). */
    private static record Pos(int row, int col) {}

    /**
     * Chooses an empty cell that has the fewest legal candidates (MRV heuristic).
     * Returns {@code null} if the board has an empty cell with no legal digit
     * (i.e. the current branch is a dead end).
     */
    private static Pos chooseCell(SudokuBoard board) {
        int bestCount = Integer.MAX_VALUE;
        Pos best      = null;
        for (int row = 0; row < 9; row++){
            for (int col = 0; col < 9; col++) {
                Cell cell = board.getCell(col, row);

//                if (!board.isEmpty(row, col)) continue;
                if(cell.getContent() != 0) continue;
//                int cnt = board.candidateCount(row, col);
                int cnt = cell.getPossibleContent().size();
                if (cnt == 0) return null;        // dead end
                if (cnt < bestCount) {
                    bestCount = cnt;
                    best      = new Pos(row, col);
                }
            }
        }
        return best;
    }

    /**
     * Recursive depth‑first search. Each recursive call represents one node in
     * the implicit search tree. Returns an {@link Optional} containing the solved
     * board, or an empty Optional if this branch fails.
     */
    private static Optional<SudokuBoard> solve(Node node) {
        // 1. Fill deterministically as far as possible
        SolvingSudokus solving = new SolvingSudokus();
        solving.solveByReasoningAsFarAsPossible(node.sudoku);

        if (!node.sudoku.isValid())            // contradiction detected
            return Optional.empty();
        if (node.sudoku.isSolved())        // board solved
            return Optional.of(node.sudoku);

        // 2. Branching point: pick a cell and try all candidates
        Pos p = chooseCell(node.sudoku);
        if (p == null) return Optional.empty();  // no legal candidates left


        Cell cell = node.sudoku.getCell(p.col(), p.row());
        //List<Integer> candidates = node.sudoku.candidates(p.row(), p.col());
        List<Integer> candidates = cell.getPossibleContent();
        for (int digit : candidates) {
            SudokuBoard next = node.sudoku.copy();
            Cell nextCell = next.getCell(p.col(), p.row());
            //next.tryCell(p.row(), p.col(), digit);   // speculative assignment
            nextCell.setContent(digit);
            if (!next.isValid()) continue;           // immediate contradiction

            Node child = new Node(next, node,
                    "R" + (p.row() + 1) + "C" + (p.col() + 1) + " = " + digit);
            Optional<SudokuBoard> solved = solve(child);
            if (solved.isPresent()) return solved;  // propagate success up the stack
        }
        // all children failed
        return Optional.empty();
    }

    /**
     * Public API: attempts to solve the given starting board.
     *
     * @param start the board to solve (must not be {@code null})
     * @return an {@link Optional} containing the solved board, or empty if the
     *         puzzle is unsolvable under standard Sudoku rules
     */
    public static Optional<SudokuBoard> solveSudoku(SudokuBoard start) {
        Objects.requireNonNull(start, "board must not be null");
        return solve(new Node(start, null, "root"));
    }

    private SudokuSolver() { /* utility class – no instances */ }
}
