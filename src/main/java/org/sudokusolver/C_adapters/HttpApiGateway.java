package org.sudokusolver.C_adapters;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.B_useCases.UseCase2HttpGatewayOutputPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpApiGateway implements UseCase2HttpGatewayOutputPort {
    /**
     * "DOSUKU-Api auf https://sudoku-api.vercel.app/"
     */

    private static final Logger log = LoggerFactory.getLogger(HttpApiGateway.class);

    public List<String> getSudokuJsonStrings() {
        List<String> jsonStrings = new ArrayList<>();
        for (int i = 0; i < AdapterConf.numberOfGridsToDownload; i++) {
            String json = getSudokuJsonString();
            jsonStrings.add(json);
        }
        return jsonStrings;
    }

    public String getSudokuJsonString() {
        HttpURLConnection conn = null;
        String json = null;
        try {
            conn = getHttpConnection();
            json = tryToGetJsonStrings(conn);
        } catch (Exception e) {
            log.error("Fehler beim Abrufen des Sudokus.", e);
        } finally {
            if (conn != null) conn.disconnect();
        }
        return json;
    }

    private HttpURLConnection getHttpConnection() throws IOException {
        HttpURLConnection conn;
        URL url = new URL("https://sudoku-api.vercel.app/api/dosuku");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(AdapterConf.connectionTimeOut);
        conn.setReadTimeout(AdapterConf.readTimeOut);
        return conn;
    }

    private String tryToGetJsonStrings(HttpURLConnection conn) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            // Whitespace entfernen durch Re-Serialisieren
            JsonElement jsonElement = JsonParser.parseString(response.toString());
            // kompakter JSON-String
            return new Gson().toJson(jsonElement);
        }
    }



}
