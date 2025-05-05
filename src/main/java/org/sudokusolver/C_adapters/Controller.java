package org.sudokusolver.C_adapters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.B_useCases.UseCaseInputPort;
import java.nio.file.Path;
import java.util.List;

public class Controller {

    UseCaseInputPort useCaseInputPort;
    Presenter presenter;
    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    public Controller(UseCaseInputPort useCaseInputPort) {
        this.useCaseInputPort = useCaseInputPort;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void loadSudoku(Path sudokuDatei){
        List<Cell> cells = useCaseInputPort.loadSudoku(sudokuDatei);
        presenter.setCells(cells);
        reducePossibilitiesFromCurrentState();
        presenter.refreshBoard();
    }

    public void solveSudokuOneStep(){
        useCaseInputPort.solveOneStep();
    }

    public void solveSudokuOneStepInContext(){
        useCaseInputPort.solveSudokuOneStepInContext();
    }

    public void solveByReasoningAsFarAsPossible(){
        useCaseInputPort.solveByReasoningAsFarAsPossible();
    }

    public void reducePossibilitiesFromCurrentState(){
        useCaseInputPort.reducePossibilitiesFromCurrentState();
    }

    public void handleKeyPressed(String key) {
        if(key.matches("[1-9]")){
            presenter.handleDigit(key);
            reducePossibilitiesFromCurrentState();
            presenter.refreshBoard();
        } else {
            switch (key.toUpperCase()) {
                case "A":
                    solveByReasoningAsFarAsPossible();
                    presenter.refreshBoard();
                    break;
                case "T":
                    // Try
                    tryRecursively();
                    presenter.refreshBoard();
                    break;
                case "C":
                    // Context
                    solveSudokuOneStepInContext();
                    reducePossibilitiesFromCurrentState();
                    presenter.refreshBoard();
                    break;
                case "S":
                    // Solve
                    solveSudokuOneStep();
                    reducePossibilitiesFromCurrentState();
                    presenter.refreshBoard();
                    break;
                default:
                    log.info("Andere Taste: " + key + ". Funktionstasten: A C S ");
            }
        }
    }
    void tryRecursively(){
        useCaseInputPort.tryRecursively();

    }

    public void cellClicked(int row, int col){
        presenter.cellClicked(row, col);
    }
    // im Controller
    public void downloadSudokus() {
        useCaseInputPort.downloadSudokusFromApiAndStore();   // Use‑Case aufrufen
        // optional: presenter.refreshBoard(...) o. Ä., falls direkt ein neues
        // Sudoku angezeigt werden soll
    }

}
