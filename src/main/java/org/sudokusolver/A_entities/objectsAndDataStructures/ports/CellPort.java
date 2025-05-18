package org.sudokusolver.A_entities.objectsAndDataStructures.ports;

import java.awt.*;
import java.util.List;

public interface CellPort {
    int getContent();
    Point getPosition();
    List<Integer> getPossibleContent();
    boolean isValid();
}
