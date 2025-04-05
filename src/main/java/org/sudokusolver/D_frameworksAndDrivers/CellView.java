package org.sudokusolver.D_frameworksAndDrivers;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.util.Set;

public class CellView extends StackPane {

    private final Label bigLabel = new Label();
    private final GridPane smallGrid = new GridPane();
    private final Label[][] smallLabels = new Label[3][3];

    public CellView() {
        // 1) Big Label für festen Wert
        bigLabel.setFont(Font.font(36));
        bigLabel.setAlignment(Pos.CENTER);
        // Optional: Größe festlegen, Ausrichtung etc.

        // 2) GridPane (3×3) für mögliche Kandidaten
        smallGrid.setPrefSize(50, 50); // Beispielwerte, je nach Layout
        smallGrid.setAlignment(Pos.CENTER);

        // 3) Kleine Labels in der 3×3-Matrix anlegen
        //    Für jeden der 9 Plätze ein Label (Font: 1/3 des großen)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Label candidateLabel = new Label();
                candidateLabel.setFont(Font.font(12)); // z.B. 1/3 von 36
                candidateLabel.setAlignment(Pos.CENTER);
                smallGrid.add(candidateLabel, col, row);
                smallLabels[row][col] = candidateLabel;
            }
        }

        // Zellenreihenfolge (oben links bis unten rechts)
        //  1 in [0][0], 2 in [0][1], 3 in [0][2],
        //  4 in [1][0], 5 in [1][1], ...
        // Das Mapping der Positionen kommt später in setPossibles()

        // 4) Alles in den StackPane legen
        //    Der StackPane überlagert also bigLabel und das GridPane
        getChildren().addAll(smallGrid, bigLabel);

        // Am Anfang: nichts gesetzt -> Grid der Kandidaten anzeigen, bigLabel verstecken
        showCandidates(true);
    }

    /**
     * Zeigt oder versteckt das Grid der Kandidaten und den großen Label.
     */
    private void showCandidates(boolean show) {
        smallGrid.setVisible(show);
        bigLabel.setVisible(!show);
    }

    /**
     * Setzt einen festen Wert (1–9). Ist value null, dann kein Wert.
     */
    public void setValue(Integer value) {
        if (value == null) {
            // Kein Wert -> nur Kandidaten
            bigLabel.setText("");
            showCandidates(true);
        } else {
            // Fester Wert -> nur den großen Label anzeigen
            bigLabel.setText(value.toString());
            showCandidates(false);
        }
    }

    /**
     * Setzt die möglichen Kandidaten. Am besten als Set<Integer> (z.B. {1, 2, 5}).
     */
    public void setPossibles(Set<Integer> possibles) {
        // Z.B. Mapping: (row, col) -> Zahl
        //    1 -> (0,0), 2 -> (0,1), 3 -> (0,2),
        //    4 -> (1,0), 5 -> (1,1), 6 -> (1,2),
        //    7 -> (2,0), 8 -> (2,1), 9 -> (2,2)
        // Dann setzen wir das Label entweder mit der Zahl oder leer:
        for (int num = 1; num <= 9; num++) {
            int index = num - 1;
            int row = index / 3;
            int col = index % 3;

            if (possibles.contains(num)) {
                smallLabels[row][col].setText(Integer.toString(num));
            } else {
                smallLabels[row][col].setText("");
            }
        }
    }
}

