package org.sudokusolver.C_adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.sudokusolver.A_entities.objectsAndDataStructures.Position;

public class ViewModel {
    private List<CellDto> cellList;
    private Position highlighted = null;

    private static final Logger log = LoggerFactory.getLogger(ViewModel.class);

    public void setCellList(List<CellDto> cellList) {
        this.cellList = cellList;
    }

    public List<CellDto> getCellList() {
        return cellList;
    }


    public Position getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(Position highlighted) {
        this.highlighted = highlighted;
    }
}
