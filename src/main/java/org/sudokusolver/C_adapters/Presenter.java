package org.sudokusolver.C_adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;

import java.awt.*;
import java.util.List;

public class Presenter {
    private final ViewModel viewModel;
    private final Presenter2ViewOutputPort presenter2ViewOutputPort;
    private static final Logger log = LoggerFactory.getLogger(Presenter.class);

    public Presenter(ViewModel viewModel, Presenter2ViewOutputPort presenter2ViewOutputPort) {
        this.viewModel = viewModel;
        this.presenter2ViewOutputPort = presenter2ViewOutputPort;
        presenter2ViewOutputPort.setKeyToPressable();
    }

    public void cellClicked(Point position){
        List<Point> clickedCells = presenter2ViewOutputPort.highlightCellBasedOnStatus(position);
        viewModel.setClickedCells(clickedCells);
    }

    List<Point> getClickedCells(){
        return viewModel.getClickedCells();
    }


    boolean oneCellClicked(){
        List<Point> clickedCells = getClickedCells();
        return clickedCells.size() == 1;
    }


    void setCells(List<Cell> cells){
        viewModel.setCellList(cells);
    }

    void refreshBoard(){

        presenter2ViewOutputPort.refreshBoard(viewModel.getCellList());
    }


}
