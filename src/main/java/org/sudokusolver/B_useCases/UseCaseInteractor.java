package org.sudokusolver.B_useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.A_entities.objectsAndDataStructures.SolvingSudokus;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

import java.nio.file.Path;
import java.util.List;

public class UseCaseInteractor implements UseCaseInputPort {
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
        solve.setSudoku(sudoku);
        return sudoku.getCells();

    }

    public void solveOneStep(){
        solve.testForSinglePossibilitiesAndFillIn();
    }

    public void solveOneStepInContext(){
        solve.testForSinglePossibilitiesInContextAndFillIn();
    }

    public void reducePossibilitiesFromCurrentState(){
        solve.reducePossibilitiesFromCurrentState();
        //solve.printOutPossibilities();
    }

    public void downloadSudokusFromApiAndStore(){
        String json = download.downloadJsonStrings();
        saveFile.saveToJsonFileNamedByDate(json);
    }
}
