package org.sudokusolver.B_useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.BacktrackingSolver;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.A_entities.objectsAndDataStructures.DeterministicSolver;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.A_entities.objectsAndDataStructures.Position;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class UseCaseInteractor implements UseCaseInputPort {
    SudokuBoard sudoku;
    LoadFileUseCase loadFile = new LoadFileUseCase();
    SaveFileUseCase saveFile;
    ParseSudokuFromJsonStringUseCase parse = new ParseSudokuFromJsonStringUseCase();
    DownloadSudokuFromApiUseCase download;
    DeterministicSolver deterministicSolver = new DeterministicSolver();
    BacktrackingSolver backtrackingSolver = new BacktrackingSolver();
    private static final Logger log = LoggerFactory.getLogger(UseCaseInteractor.class);

    public UseCaseInteractor(
            UseCase2HttpGatewayOutputPort useCase2HttpGatewayOutputPort,
            UseCase2FilesystemOutputPort useCase2FilesystemOutputPort
            ) {
        this.download = new DownloadSudokuFromApiUseCase(useCase2HttpGatewayOutputPort);
        this.saveFile = new SaveFileUseCase(useCase2FilesystemOutputPort);
    }

    public List<Cell> loadSudoku(Path sudokuFile) {
        String jsonString = loadFile.loadJsonFile(sudokuFile);
        SudokuBoard sudoku = parse.parse(jsonString);
        this.sudoku = sudoku;
        return sudoku.getCells();

    }

    public void solveOneStep(){
        deterministicSolver.testForSinglePossibilitiesInCellAndFillIn();
    }

    public void solveSudokuOneStepInUnit(){
        deterministicSolver.testForSinglePossibilitiesInUnitAndFillIn(sudoku);
    }

    public void solveByReasoningAsFarAsPossible(){
        deterministicSolver.solveByReasoningAsFarAsPossible(sudoku);
    }

    public void reducePossibilitiesFromCurrentState(){
        deterministicSolver.reducePossibilitiesFromCurrentState(sudoku);
        //solve.printOutPossibilities();
    }

    public void downloadSudokuFromApiAndStore(){
        String json = download.downloadJsonStrings();
        saveFile.saveToJsonFileNamedByDate(json);
    }

    public List<Cell> solveRecursively(){
        SudokuBoard solved = backtrackingSolver.solve(sudoku);
        if ( solved != null) {
            log.info("=== Gelöst ===");
            this.sudoku = solved;
            return solved.getCells();
        } else {
            log.info("Unlösbar.");
            return Collections.emptyList();
        }
    }

    @Override
    public void handleKeyInputWithCellClickedAtPosition(int value, Position clickedCell) {
        Cell cell = sudoku.getCell(clickedCell);
        if(cell.getContent() == value){
            cell.setContent(0);
        } else {
            cell.setContent(value);
        }
        sudoku.resetPossiblesInAllCells();
        deterministicSolver.reducePossibilitiesFromCurrentState(sudoku);
    }

    @Override
    public List<Cell> validateSudoku() {
        sudoku.validate();
        return sudoku.getCells();
    }
}
