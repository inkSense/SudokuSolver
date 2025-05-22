package org.sudokusolver.B_useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objects.BacktrackingSolver;
import org.sudokusolver.A_entities.objects.Cell;
import org.sudokusolver.A_entities.objects.DeterministicSolver;
import org.sudokusolver.A_entities.objects.SudokuBoard;
import org.sudokusolver.A_entities.dataStructures.Position;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UseCaseInteractor implements UseCaseInputPort {
    SudokuBoard sudoku;
    LoadFileUseCase loadFile = new LoadFileUseCase();
    SaveFileUseCase saveFile;
    SerializeDeserializeSudokuJsonStringUseCase serializeDeserialize = new SerializeDeserializeSudokuJsonStringUseCase();
    DownloadSudokuFromApiUseCase download;
    FillInManuallyUseCase fillIn = new FillInManuallyUseCase();
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

    public List<Cell> loadSudoku(String sudokuFileName) {
        String jsonString = loadFile.loadJsonFile(sudokuFileName);
        SudokuBoard sudoku = serializeDeserialize.parse(jsonString);
        this.sudoku = sudoku;
        return sudoku.getCells();

    }

    @Override
    public Path getLastLoadedFile() {
        return loadFile.getLastLoadedFile();
    }

    public List<String> getAllDataFileNames(){
        return loadFile.getAllDataFileNames();
    }

    public void saveSudoku(String fileName) {
        String content = serializeDeserialize.serialize(sudoku);
        saveFile.saveToJsonFile(content, fileName);
    }

    public void downloadSudokuFromApiAndStore(){
        String json = download.downloadJsonStrings();
        saveFile.saveToJsonFileNamedByDate(json);
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
        fillIn.handleCellInput(value, cell);
        sudoku.resetPossiblesInAllCells();
        deterministicSolver.reducePossibilitiesFromCurrentState(sudoku);
    }

    @Override
    public List<Cell> validateSudoku() {
        sudoku.validate();
        return sudoku.getCells();
    }
}
