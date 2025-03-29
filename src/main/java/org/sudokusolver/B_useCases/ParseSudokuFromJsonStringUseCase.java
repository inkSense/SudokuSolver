package org.sudokusolver.B_useCases;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

public class ParseSudokuFromJsonStringUseCase {
    public SudokuBoard parse(String jsonString){
        JsonObject root = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonArray grid = root.getAsJsonObject("newboard")
                .getAsJsonArray("grids")
                .get(0)
                .getAsJsonObject()
                .getAsJsonArray("value");
        Cell[][] board = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            JsonArray row = grid.get(i).getAsJsonArray();
            for (int j = 0; j < 9; j++) {
                int content = row.get(j).getAsInt();
                board[i][j] = new Cell(content);
            }
        }
        return new SudokuBoard(board);
    }
}
