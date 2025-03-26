package org.sudokusolver.D_frameworksAndDrivers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.sudokusolver.C_adapters.AdapterOutputPort;

public class FxView implements AdapterOutputPort {

    @FXML
    private GridPane gridPane;

    private final TextField[][] fields = new TextField[9][9];

    @FXML
    public void initialize() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField tf = new TextField();
                tf.setPrefSize(40, 40);
                tf.setStyle("-fx-font-size: 16; -fx-alignment: center;");
                fields[row][col] = tf;
                gridPane.add(tf, col, row);
            }
        }
    }

    @Override
    public void displaySudoku(/* Übergib hier später z. B. eine 2D-Cell-Matrix */) {
        // Logik zur Anzeige von Zellen kommt später hier rein
    }
}
