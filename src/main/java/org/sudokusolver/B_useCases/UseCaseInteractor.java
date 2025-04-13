package org.sudokusolver.B_useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.SolvingSudokus;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

import java.util.List;

public class UseCaseInteractor implements UseCaseInputPort {
    LoadFileUseCase loadFile = new LoadFileUseCase();
    SaveFileUseCase saveFile;
    ParseSudokuFromJsonStringUseCase parse = new ParseSudokuFromJsonStringUseCase();
    DownloadSudokuFromApiUseCase download;
    SolvingSudokus solve = new SolvingSudokus();
    SudokuBoard sudoku;
    private static final Logger log = LoggerFactory.getLogger(UseCaseInteractor.class);

    public UseCaseInteractor(
            UseCase2HttpGatewayOutputPort useCase2HttpGatewayOutputPort,
            UseCase2FilesystemOutputPort useCase2FilesystemOutputPort
            ) {
        this.download = new DownloadSudokuFromApiUseCase(useCase2HttpGatewayOutputPort);
        this.saveFile = new SaveFileUseCase(useCase2FilesystemOutputPort);
    }

    public List<SudokuBoard> loadSudokus(){
        List<String> jsons = loadFile.loadJsonStrings();

        if(jsons.isEmpty()){
            log.info("No File. Downloading.");
            List<String> strings = download.downloadJsonStrings();
            log.info("Safing to File " + ApplicationConf.dataPathString);
            saveFile.save(strings);
        }

        return parse.parse(jsons);
    }

    public void setSudoku(SudokuBoard sudoku){
        this.sudoku = sudoku;
        solve.setSudoku(sudoku);
    }

    public void solveOneStep(){
        solve.testForSinglePossibilitiesAndFillIn();

        sudoku.print();
    }

    public void solveOneStepInContext(){
        solve.testForSinglePossibilitiesInContextAndFillIn();
    }

    public void reducePossibilitiesFromCurrentState(){
        solve.reducePossibilitiesFromCurrentState();
        solve.printOutPossibilities();
    }
}
