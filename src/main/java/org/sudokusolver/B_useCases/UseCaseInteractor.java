package org.sudokusolver.B_useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.BacktrackingSolver;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.A_entities.objectsAndDataStructures.SolvingSudokus;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class UseCaseInteractor implements UseCaseInputPort {
    SudokuBoard sudoku;
    LoadFileUseCase loadFile = new LoadFileUseCase();
    SaveFileUseCase saveFile;
    ParseSudokuFromJsonStringUseCase parse = new ParseSudokuFromJsonStringUseCase();
    DownloadSudokuFromApiUseCase download;
    SolvingSudokus solve = new SolvingSudokus();
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
        solve.testForSinglePossibilitiesAndFillIn();
    }

    public void solveSudokuOneStepInContext(){
        solve.testForSinglePossibilitiesInContextAndFillIn(sudoku);
    }

    public void solveByReasoningAsFarAsPossible(){
        solve.solveByReasoningAsFarAsPossible(sudoku);
    }

    public void reducePossibilitiesFromCurrentState(){
        solve.reducePossibilitiesFromCurrentState(sudoku);
        //solve.printOutPossibilities();
    }

    public void downloadSudokusFromApiAndStore(){
        String json = download.downloadJsonStrings();
        saveFile.saveToJsonFileNamedByDate(json);
    }

    public List<Cell> tryRecursively(){
//        SudokuBoard solvedSudoku = solve.solve();
//        List<Cell> solvedCells = solvedSudoku.getCells();
//
//        if (solvedSudoku.isSolved()) {
//            log.info("Sudoku gelöst");
//            log.info("Untersuchte Blätter: {}", solve.examinedLeaves());
//            return solvedSudoku.getCells();
//        } else {
//            log.info("Sudoku ist unlösbar.");
//            return Collections.emptyList();
//        }

        BacktrackingSolver solver = new BacktrackingSolver();
        SudokuBoard solved = solver.solve(sudoku);

        if ( solved != null) {
            log.info("=== Gelöst ===");
            return solved.getCells();
        } else {
            log.info("Unlösbar.");
            return Collections.emptyList();
        }
    }

}
