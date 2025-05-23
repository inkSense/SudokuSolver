package org.sudokusolver.B_useCases;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.sudokusolver.A_entities.objects.Cell;
import org.sudokusolver.A_entities.dataStructures.Position;
import org.sudokusolver.A_entities.objects.SudokuBoard;

import java.util.ArrayList;
import java.util.List;

public class SerializeDeserializeSudokuJsonStringUseCase {

    public SudokuBoard parse(String jsonString){
        JsonObject root = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject firstGrid = root
                .getAsJsonObject("newboard")
                .getAsJsonArray("grids")
                .get(0).getAsJsonObject();
        JsonArray jsonArray = firstGrid.getAsJsonArray("value");
        List<Cell> cells = buildCellList(jsonArray);
        String difficulty = firstGrid.get("difficulty").getAsString();
        return new SudokuBoard(cells, difficulty);
    }

    private List<Cell> buildCellList(JsonArray jsonArray){
        List<Cell> cells = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            JsonArray rowArray = jsonArray.get(row).getAsJsonArray();
            for (int col = 0; col < 9; col++) {
                int content = rowArray.get(col).getAsInt();
                cells.add(new Cell(content, new Position(row, col)));
            }
        }
        return cells;
    }

    public String parseDifficulty(String jsonString){
        JsonObject root = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonArray grids = root.getAsJsonObject("newboard")
                .getAsJsonArray("grids");
        JsonObject firstGrid = grids.get(0).getAsJsonObject();
        return firstGrid.get("difficulty").getAsString();
    }

    public String serialize(SudokuBoard board) {
        JsonObject root = new JsonObject();
        JsonObject newboard = new JsonObject();
        JsonArray  grids = new JsonArray();
        JsonObject gridEntry = new JsonObject();

        JsonArray value = buildJsonArray(board);

        //Grid-Eintrag zusammenbauen
        gridEntry.add("value", value);
        gridEntry.addProperty("difficulty", board.getDifficulty());
        grids.add(gridEntry);

        // Objekt-Hierarchie fertigstellen
        newboard.add("grids", grids);
        root.add("newboard", newboard);

        return root.toString();             // Pretty-Print: new GsonBuilder().setPrettyPrinting().create().toJson(root);
    }

    private JsonArray buildJsonArray(SudokuBoard board){
        JsonArray value = new JsonArray();
        for (int row = 0; row < 9; row++) {
            JsonArray rowArr = new JsonArray();
            for (int col = 0; col < 9; col++) {
                int v = board.getCell(new Position(row, col)).getContent();
                rowArr.add(v);                    // 0 = leer, 1-9 sonst
            }
            value.add(rowArr);
        }
        return value;
    }
}
