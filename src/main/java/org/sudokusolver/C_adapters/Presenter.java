package org.sudokusolver.C_adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

import org.sudokusolver.A_entities.objectsAndDataStructures.Position;

public class Presenter {
    private final ViewModel viewModel;
    private final Presenter2ViewOutputPort presenter2ViewOutputPort;
    private static final Logger log = LoggerFactory.getLogger(Presenter.class);

    public Presenter(ViewModel viewModel, Presenter2ViewOutputPort presenter2ViewOutputPort) {
        this.viewModel = viewModel;
        this.presenter2ViewOutputPort = presenter2ViewOutputPort;
        presenter2ViewOutputPort.setKeyToPressable();
    }

    public void cellClicked(Position position){
        updateModel(position);
        presenter2ViewOutputPort.unHighlightAllCells();
        Position highlighted = viewModel.getHighlighted();
        if(highlighted != null){
            presenter2ViewOutputPort.highlightCell(highlighted);
        }
    }

    Optional<Position> getHighlightedCell(){
        return Optional.ofNullable(viewModel.getHighlighted());
    }

    void setCells(List<CellDto> cells){
        viewModel.setCellList(cells);
    }

    void refreshBoard(){
        presenter2ViewOutputPort.refreshBoard(viewModel.getCellList());
    }

    public void updateModel(Position clickPosition) {
        Position modelPosition = viewModel.getHighlighted();
        boolean sameClickedTwice = modelPosition != null && modelPosition.equals(clickPosition);
        if(sameClickedTwice){
            viewModel.setHighlighted(null);
        } else {
            viewModel.setHighlighted(clickPosition);
        }
    }


}
