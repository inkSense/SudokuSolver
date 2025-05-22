package org.sudokusolver.C_adapters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objects.Cell;
import org.sudokusolver.A_entities.dataStructures.Position;
import org.sudokusolver.B_useCases.UseCaseInputPort;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

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
        List<CellDto> cellDtos = cells.stream().map(CellMapper::toDto).toList();

        presenter.setCells(cellDtos);
        useCaseInputPort.reducePossibilitiesFromCurrentState();
        validateAndRefresh();
    }

    public void solveSudokuOneStep(){
        useCaseInputPort.solveOneStep();
        useCaseInputPort.reducePossibilitiesFromCurrentState();
        validateAndRefresh();
    }

    public void solveSudokuOneStepInUnit(){
        useCaseInputPort.solveSudokuOneStepInUnit();
        useCaseInputPort.reducePossibilitiesFromCurrentState();
        validateAndRefresh();
    }

    public void solveByReasoningAsFarAsPossible(){
        useCaseInputPort.solveByReasoningAsFarAsPossible();
        validateAndRefresh();
    }

    public void handleKeyPressed(String key) {
        if(key.matches("[1-9]")){
            handleDigit(key);
        } else {
            switch (key.toUpperCase()) {
                case "A" -> solveByReasoningAsFarAsPossible(); // All
                case "T" -> solveByReasoningAndRecursively(); // Try
                case "U" -> solveSudokuOneStepInUnit(); // Unit
                case "S" -> solveSudokuOneStep(); // Solve
                default -> log.info("Andere Taste: " + key + ". Funktionstasten: A T U S ");
            }
        }
    }

    void handleDigit(String digitString){
        int value = Integer.parseInt(digitString);
        log.info("int: " + value);
        Optional<Position> highlighted = presenter.getHighlightedCell();
        if(highlighted.isPresent()) {
            useCaseInputPort.handleKeyInputWithCellClickedAtPosition(value, highlighted.get());
            validateAndRefresh();
        }
    }

    public void solveByReasoningAndRecursively(){
        List<Cell> cells = useCaseInputPort.solveRecursively();
        List<CellDto> cellDtos = cells.stream().map(CellMapper::toDto).toList();
        presenter.setCells(cellDtos);
        validateAndRefresh();
    }

    public void cellClicked(Position position){
        presenter.cellClicked(position);
    }

    public void downloadSudoku() {
        useCaseInputPort.downloadSudokuFromApiAndStore();
    }

    private void validateAndRefresh(){
        List<Cell> cells = useCaseInputPort.validateSudoku();
        List<CellDto> cellDtos = cells.stream().map(CellMapper::toDto).toList();
        presenter.setCells(cellDtos);
        presenter.refreshBoard();
    }

}
