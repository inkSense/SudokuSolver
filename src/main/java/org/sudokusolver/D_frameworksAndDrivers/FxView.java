package org.sudokusolver.D_frameworksAndDrivers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.sudokusolver.C_adapters.AdapterOutputPort;

public class FxView implements AdapterOutputPort {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
