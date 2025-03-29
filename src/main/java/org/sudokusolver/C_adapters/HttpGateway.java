package org.sudokusolver.C_adapters;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.B_useCases.ParseSudokuFromJsonStringUseCase;
import org.sudokusolver.B_useCases.UseCaseOutputPort;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGateway implements UseCaseOutputPort {

    private static final Logger log = LoggerFactory.getLogger(HttpGateway.class);

    @Override
    public SudokuBoard getSudoku() {
        String jsonString = fetchSudokuJsonString();
        return parseSudoku(jsonString);
    }

    public static SudokuBoard parseSudoku(String jsonString){
        var useCase = new ParseSudokuFromJsonStringUseCase();
        return useCase.parse(jsonString);
    }

    public String fetchSudokuJsonString() {
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

                // Whitespace entfernen durch kompaktes Re-Serialisieren
                JsonElement jsonElement = JsonParser.parseString(response.toString());
                return new Gson().toJson(jsonElement); // kompakter JSON-String
            }
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Abrufen des Sudokus", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

}
