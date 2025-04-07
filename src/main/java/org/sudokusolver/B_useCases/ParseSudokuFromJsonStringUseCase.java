package org.sudokusolver.B_useCases;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

import java.util.ArrayList;
import java.util.List;

public class ParseSudokuFromJsonStringUseCase {
    public List<SudokuBoard> parse(List<String> jsonStrings){
        List<SudokuBoard> sudokus = new ArrayList<>();
        for(String string : jsonStrings){
            sudokus.add(parse(string));
        }
        return sudokus;
    }


    private SudokuBoard parse(String jsonString){
        JsonObject root = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject firstGrid = root
                .getAsJsonObject("newboard")
                .getAsJsonArray("grids")
                .get(0).getAsJsonObject();
        JsonArray jsonArray = firstGrid.getAsJsonArray("value");
        String difficulty = firstGrid.get("difficulty").getAsString();
        Cell[][] board = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            JsonArray row = jsonArray.get(i).getAsJsonArray();
            for (int j = 0; j < 9; j++) {
                int content = row.get(j).getAsInt();
                board[i][j] = new Cell(content);
            }
        }
        return new SudokuBoard(board, difficulty);
    }
    public String parseDifficulty(String jsonString){
        JsonObject root = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonArray grids = root.getAsJsonObject("newboard")
                .getAsJsonArray("grids");
        JsonObject firstGrid = grids.get(0).getAsJsonObject();
        String difficulty = firstGrid.get("difficulty").getAsString();
        return difficulty;
    }
}
