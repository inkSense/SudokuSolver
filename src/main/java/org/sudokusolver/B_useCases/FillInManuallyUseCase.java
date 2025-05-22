package org.sudokusolver.B_useCases;

import org.sudokusolver.A_entities.objects.Cell;

public class FillInManuallyUseCase {
    public void handleCellInput(int value, Cell cell){
        if(cell.getContent() == value){
            cell.setContent(0);
        } else {
            cell.setContent(value);
        }
    }
}
