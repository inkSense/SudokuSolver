package org.sudokusolver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.B_useCases.LoadSudokuUseCase;
import org.sudokusolver.C_adapters.RepositoryGateway;
import org.sudokusolver.C_adapters.Controller;

public class Main extends Application {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/sudokusolver/sudoku-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Sudoku Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        //launch(args);
        var api = new RepositoryGateway();
        var useCase = new LoadSudokuUseCase(api);
        var controller = new Controller(useCase);
        controller.downloadSudokuAndPrintItOut();
    }
}
