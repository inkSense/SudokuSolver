package org.sudokusolver.C_adapters;

import org.sudokusolver.A_entities.objectsAndDataStructures.Position;
import java.util.List;

public interface Presenter2ViewOutputPort {
    void refreshBoard(List<CellDto> cellList);
    void setKeyToPressable();
    void unHighlightAllCells();
    void highlightCell(Position position);

}
