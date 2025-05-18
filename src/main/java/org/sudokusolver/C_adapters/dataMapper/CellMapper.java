package org.sudokusolver.C_adapters.dataMapper;

import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;


public final class CellMapper {
    public record CellDto(int row, int col, int value, boolean valid) { }
    private CellMapper() { }

    /** Domain → DTO */
    public static CellDto toDto(Cell cell) {
        return new CellDto(
                cell.getPosition().y,
                cell.getPosition().x,
                cell.getContent(),
                cell.isValid()
        );
    }

    /** DTO → Domain (falls UI zurückschreibt) */
    public static Cell toEntity(CellDto dto) {
        Cell cell = new Cell(dto.value(), dto.row(), dto.col());
        cell.setValid(dto.valid());
        return cell;
    }
}

