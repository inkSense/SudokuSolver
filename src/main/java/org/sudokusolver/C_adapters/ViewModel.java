package org.sudokusolver.C_adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.sudokusolver.A_entities.dataStructures.Position;

public class ViewModel {
    private List<CellDto> cellList;
    private Position highlighted = null;

    private static final Logger log = LoggerFactory.getLogger(ViewModel.class);

    void setCellList(List<CellDto> cellList) {
        this.cellList = cellList;
    }

    List<CellDto> getCellList() {
        return cellList;
    }

    Position getHighlighted() {
        return highlighted;
    }

    void setHighlighted(Position highlighted) {
        this.highlighted = highlighted;
    }
}
