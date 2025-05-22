package org.sudokusolver.C_adapters;

import java.util.List;
import org.sudokusolver.A_entities.dataStructures.Position;

public record CellDto(Position position, int content, boolean valid, List<Integer> possibles) { }
