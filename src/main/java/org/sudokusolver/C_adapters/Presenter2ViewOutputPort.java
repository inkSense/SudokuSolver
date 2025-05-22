package org.sudokusolver.C_adapters;

import org.sudokusolver.A_entities.dataStructures.Position;
import java.util.List;

public interface Presenter2ViewOutputPort {
    void refreshBoard(List<CellDto> cellList);
    void unHighlightAllCells();
    void highlightCell(Position position);
}
