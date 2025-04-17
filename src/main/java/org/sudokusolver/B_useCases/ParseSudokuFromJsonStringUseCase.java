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


    public SudokuBoard parse(String jsonString){
        JsonObject root = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject firstGrid = root
                .getAsJsonObject("newboard")
                .getAsJsonArray("grids")
                .get(0).getAsJsonObject();
        JsonArray jsonArray = firstGrid.getAsJsonArray("value");
        String difficulty = firstGrid.get("difficulty").getAsString();
        List<Cell> cells = new ArrayList<>();

        for (int row = 0; row < 9; row++) {
            JsonArray rowArray = jsonArray.get(row).getAsJsonArray();
            for (int col = 0; col < 9; col++) {
                int content = rowArray.get(col).getAsInt();
                cells.add(new Cell(content, row, col));
            }
        }
        return new SudokuBoard(cells, difficulty);
    }

    public String parseDifficulty(String jsonString){
        JsonObject root = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonArray grids = root.getAsJsonObject("newboard")
                .getAsJsonArray("grids");
        JsonObject firstGrid = grids.get(0).getAsJsonObject();
        return firstGrid.get("difficulty").getAsString();
    }
}
