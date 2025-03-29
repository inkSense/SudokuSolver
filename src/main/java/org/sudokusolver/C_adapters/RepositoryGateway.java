package org.sudokusolver.C_adapters;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RepositoryGateway implements AdapterOutputPort {

    private static final Logger log = LoggerFactory.getLogger(RepositoryGateway.class);

    @Override
    public SudokuBoard fetchSudoku() {
        String jsonString = fetchSudokuJsonString();
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

    private static String fetchSudokuJsonString() {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("https://sudoku-api.vercel.app/api/dosuku");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Abrufen des Sudokus", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }


    public static void main(String[] args){
        String json = fetchSudokuJsonString();
        log.info(json);
    }
}
