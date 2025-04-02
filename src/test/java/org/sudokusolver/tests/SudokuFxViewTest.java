package org.sudokusolver.tests;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.testfx.framework.junit5.ApplicationTest;
import org.sudokusolver.D_frameworksAndDrivers.FxView;

@Disabled
public class SudokuFxViewTest extends ApplicationTest {

    private FxView fxView;

    @Override
    public void start(Stage stage) throws Exception {
        // FXML laden
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/sudokusolver/D_frameworksAndDrivers/sudoku-view.fxml"));
        Parent root = loader.load();
        fxView = loader.getController();

        // Scene + Stage vorbereiten
        stage.setScene(new Scene(root));
        stage.show();
    }

}
