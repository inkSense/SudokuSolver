package org.sudokusolver.C_adapters;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.B_useCases.ApplicationConf;
import org.sudokusolver.B_useCases.ParseSudokuFromJsonStringUseCase;
import org.sudokusolver.B_useCases.UseCaseOutputPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpGateway implements UseCaseOutputPort {

    private static final Logger log = LoggerFactory.getLogger(HttpGateway.class);

    @Override
    public List<SudokuBoard> getSudokus() {
        List<String> jsonStrings = getSudokuJsonStrings();
        return parseSudokus(jsonStrings);
    }

    public static List<SudokuBoard> parseSudokus(List<String> jsonStrings){
        var useCase = new ParseSudokuFromJsonStringUseCase();
        return useCase.parse(jsonStrings);
    }

    public List<String> getSudokuJsonStrings() {
        List<String> jsonStrings = new ArrayList<>();
        for (int i = 0; i < AdapterConf.numberOfGridsToDownload; i++) {
            HttpURLConnection conn = null;
            try {
                conn = getHttpConnection();
                String json = tryToGetJsonStrings(conn);
                jsonStrings.add(json);
            } catch (Exception e) {
                log.error("Fehler beim Abrufen des Sudokus Nr." + (i + 1), e);
            } finally {
                if (conn != null) conn.disconnect();
            }
        }
        return jsonStrings;
    }


    private HttpURLConnection getHttpConnection() throws IOException {
        HttpURLConnection conn = null;
        URL url = new URL("https://sudoku-api.vercel.app/api/dosuku");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(12000);
        conn.setReadTimeout(12000);
        return conn;
    }

    private String tryToGetJsonStrings(HttpURLConnection conn) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            // Whitespace entfernen durch kompaktes Re-Serialisieren
            JsonElement jsonElement = JsonParser.parseString(response.toString());
            String json =  new Gson().toJson(jsonElement); // kompakter JSON-String
            return json;
        }
    }

}
