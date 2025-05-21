package org.sudokusolver.C_adapters;

import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;

import java.util.List;


public final class CellMapper {

    private CellMapper() { }

    public static CellDto toDto(Cell cell) {
        return new CellDto(
                cell.getPosition(),
                cell.getContent(),
                cell.isValid(),
                cell.getPossibleContent()
        );
    }

    public static Cell toEntity(CellDto dto) {
        Cell cell = new Cell(dto.content(), dto.position());
        cell.setValid(dto.valid());
        cell.setPossibleContent(dto.possibles());
        return cell;
    }
}

