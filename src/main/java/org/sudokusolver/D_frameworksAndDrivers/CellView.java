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
        int bigLabelFontSize = 45;
        int smallLabelFontSize = 15; // 1/3 von bigLabel

        // Big Label für festen Wert
        bigLabel.setFont(Font.font(bigLabelFontSize));
        bigLabel.setAlignment(Pos.CENTER);

        // GridPane (3×3) für mögliche Kandidaten
        smallGrid.setPrefSize(65, 65);
        smallGrid.setAlignment(Pos.CENTER);
        smallGrid.setHgap(12);

        createLabelsForSmallGrid(smallLabelFontSize);

        // Ins StackPane legen
        // smallGrid und bigLabel überlagern also das GridPane
        getChildren().addAll(smallGrid, bigLabel);

        // Am Anfang: nichts gesetzt -> Grid der Kandidaten anzeigen
        showCandidates(true);
    }

    public void setValue(Integer value) {
        if (value == null) {
            bigLabel.setText("");
            showCandidates(true);
        } else {
            bigLabel.setText(value.toString());
            showCandidates(false);
        }
    }

    void setPossibles(Set<Integer> possibles) {
        // Zellenreihenfolge (oben links bis unten rechts)
        //  1 in [0][0], 2 in [0][1], 3 in [0][2],
        //  4 in [1][0], 5 in [1][1], ...
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

    void setBlack(){
        bigLabel.setStyle("-fx-text-fill: black;");
    }

    void setRed(){
        bigLabel.setStyle("-fx-text-fill: red;");
    }

    void setHighlight(boolean highlight) {
        if (highlight) {
            setStyle(getStyle() + "; -fx-background-color: #ADD8E6;");
        } else {
            // Hintergrund zurücksetzen – am besten ein Basis-Style merken
            setStyle(getStyle().replaceAll("-fx-background-color:\\s?#[0-9A-Fa-f]+;", ""));
        }
    }

    private void createLabelsForSmallGrid(int fontSize){
        // Für jeden der 9 Plätze ein Label
        // Das Mapping der Positionen steht in setPossibles()
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Label candidateLabel = new Label();
                candidateLabel.setFont(Font.font(fontSize));
                candidateLabel.setAlignment(Pos.CENTER);
                smallGrid.add(candidateLabel, col, row);
                smallLabels[row][col] = candidateLabel;
            }
        }
    }

    private void showCandidates(boolean show) {
        smallGrid.setVisible(show);
        bigLabel.setVisible(!show);
    }
}

