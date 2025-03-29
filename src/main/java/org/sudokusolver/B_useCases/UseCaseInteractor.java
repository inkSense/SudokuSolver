package org.sudokusolver.B_useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

import java.util.List;

public class UseCaseInteractor implements UseCaseInputPort {
    LoadFileUseCase loadFile = new LoadFileUseCase();
    SaveFileUseCase saveFile = new SaveFileUseCase();
    ParseSudokuFromJsonStringUseCase parse = new ParseSudokuFromJsonStringUseCase();
    DownloadSudokuFromApiUseCase download;
    private static final Logger log = LoggerFactory.getLogger(UseCaseInteractor.class);

    public UseCaseInteractor(UseCaseOutputPort useCaseOutputPort) {
        this.download = new DownloadSudokuFromApiUseCase(useCaseOutputPort);
    }

    public List<SudokuBoard> loadSudokus(){
        String json = loadFile.loadJsonString();

        if(json == null){
            log.info("No File. Downloading.");
            json = download.downloadJsonString();
            log.info("JSON Antwort: \n" + json);
            log.info("Safing to File " + Conf.dataPathString);
            var saveUseCase = new SaveFileUseCase();
            saveUseCase.save(json);
        }

        SudokuBoard sudoku = parse.parse(json);
        return List.of(sudoku);

    }

    @Override
    public SudokuBoard getSudoku() {

        //Wenn eine Datei mit Sudoku da ist, dann lade die.

        //Wenn keine Datei mit Sudoku da ist, dann downloade Sudoku und speichere.

        return null;
    }
}
