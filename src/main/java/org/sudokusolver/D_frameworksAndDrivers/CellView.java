package org.sudokusolver.D_frameworksAndDrivers;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.Set;

public class CellView extends StackPane {

    private final Label bigLabel = new Label();
    private final GridPane smallGrid = new GridPane();
    private final Label[][] smallLabels = new Label[3][3];
    private boolean highlighted;
    private final Point position;

    public CellView(Point position) {
        this.position = position;

        // 1) Big Label für festen Wert
        bigLabel.setFont(Font.font(45));
        bigLabel.setAlignment(Pos.CENTER);



        // 2) GridPane (3×3) für mögliche Kandidaten
        smallGrid.setPrefSize(65, 65); // Beispielwerte, je nach Layout
        smallGrid.setAlignment(Pos.CENTER);
        smallGrid.setHgap(12);

        // 3) Kleine Labels in der 3×3-Matrix anlegen
        //    Für jeden der 9 Plätze ein Label (Font: 1/3 des großen)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Label candidateLabel = new Label();
                candidateLabel.setFont(Font.font(15)); // z.B. 1/3 von bigLabel
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

    private void showCandidates(boolean show) {
        smallGrid.setVisible(show);
        bigLabel.setVisible(!show);
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
    public void setPossibles(Set<Integer> possibles) {
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

    public void setBlack(){
        bigLabel.setStyle("-fx-text-fill: black;");
    }

    public void setRed(){
        bigLabel.setStyle("-fx-text-fill: red;");
    }

    public void setHighlight(boolean highlight) {
        highlighted = highlight;
        if (highlight) {
            setStyle(getStyle() + "; -fx-background-color: #ADD8E6;");
        } else {
            // Hintergrund zurücksetzen – am besten ein Basis-Style merken
            setStyle(getStyle().replaceAll("-fx-background-color:\\s?#[0-9A-Fa-f]+;", ""));
        }
    }


    public boolean isHighlighted() {
        return highlighted;
    }

}

