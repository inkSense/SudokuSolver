package org.sudokusolver.C_adapters;

import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;

import java.awt.*;
import java.util.List;

public interface Presenter2ViewOutputPort {
    void refreshBoard(List<Cell> cellList);
    void setKeyToPressable();
    List<Point> highlightCellBasedOnStatus(Point position);

}
