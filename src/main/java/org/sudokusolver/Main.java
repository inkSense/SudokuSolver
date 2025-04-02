package org.sudokusolver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.B_useCases.UseCaseInteractor;
import org.sudokusolver.C_adapters.HttpGateway;
import org.sudokusolver.C_adapters.Controller;
import org.sudokusolver.D_frameworksAndDrivers.FxView;

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
        launch(args);
        var api = new HttpGateway();
        var interactor = new UseCaseInteractor(api);
        var controller = new Controller(interactor);
        controller.loadOneSudokuAndSolveIt();

    }



}
