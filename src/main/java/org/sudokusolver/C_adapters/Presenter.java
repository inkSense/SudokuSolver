package org.sudokusolver.C_adapters;

import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Presenter {
    private ViewModel model;
    private Presenter2ViewOutputPort presenter2ViewOutputPort;
    private static final Logger log = LoggerFactory.getLogger(Presenter.class);

    public Presenter(ViewModel model, Presenter2ViewOutputPort presenter2ViewOutputPort) {
        this.model = model;
        this.presenter2ViewOutputPort = presenter2ViewOutputPort;

        setKeyToPressable(presenter2ViewOutputPort.getScene());
        presenter2ViewOutputPort.refreshBoard(model.getCellList());
    }

    private void setKeyToPressable(Scene scene){
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case C:
                    model.solveOneStepInContext();
                    model.reducePossibilitiesFromCurrentState();
                    presenter2ViewOutputPort.refreshBoard(model.getCellList());
                    break;
                case S:
                    model.solveSudokuOneStep();
                    model.reducePossibilitiesFromCurrentState();
                    presenter2ViewOutputPort.refreshBoard(model.getCellList());
                    break;
                default:
                    log.info("Andere Taste: " + event.getCode() + ". Funktionstasten: C S ");
            }
        });
    }






}
