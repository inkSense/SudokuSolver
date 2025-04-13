package org.sudokusolver.C_adapters;

import javafx.scene.Scene;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;

import java.util.List;

public interface Presenter2ViewOutputPort {
    void refreshBoard(List<Cell> cellList);
    Scene getScene();
}
