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

    public void cellClicked(int row, int col){
        List<Point> clickedCells = presenter2ViewOutputPort.highlightCellBasedOnStatus(row, col);
        viewModel.setClickedCells(clickedCells);
    }

    List<Point> getClickedCells(){
        return viewModel.getClickedCells();
    }

    void handleDigit(String digitString){
        int value = Integer.parseInt(digitString);
        log.info("int: " + value);

        List<Point> clickedCells = getClickedCells();
        if(clickedCells.isEmpty()){
            log.info("list of Highlighted Cells is empty");
        } else {
            // Liste hat nur ein Element
            Point position = clickedCells.get(0);
            for(Cell cell : viewModel.getCellList()){
                if(cell.getPosition().equals(position)){
                    if(cell.getContent() == value){
                        cell.setContent(0);
                    } else {
                        cell.setContent(value);
                    }
                    break;
                }
            }
            // Alle Möglichkeiten erst einmal wieder befüllen
            // Es kann nämlich sein, dass hier eine andere Zahl != 0 eingetragen wurde, die es vorher noch nicht gab
            for (Cell cell : viewModel.getCellList()) {
                cell.initializePossibleContent();
            }
        }
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
