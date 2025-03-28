package org.sudokusolver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.B_useCases.SudokuService;
import org.sudokusolver.C_adapters.ApiGateway;

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

    public static void downloadSudoku(){
        var api = new ApiGateway();
        var service = new SudokuService(api);

        SudokuBoard board = service.getSudoku();
        board.print();
    }

    public static void main(String[] args) {
        //launch(args);
        downloadSudoku();
    }
}
