package org.sudokusolver.C_adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;

import java.awt.*;
import java.util.List;

public class ViewModel {
    private List<Cell> cellList;
    private List<Point> clickedCells;

    private static final Logger log = LoggerFactory.getLogger(ViewModel.class);

    public void setCellList(List<Cell> cellList) {
        this.cellList = cellList;
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    public List<Point> getClickedCells() {
        return clickedCells;
    }

    public void setClickedCells(List<Point> clickedCells) {
        this.clickedCells = clickedCells;
    }

}
