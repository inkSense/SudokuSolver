package org.sudokusolver;


import javafx.application.Application;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.*;
import org.sudokusolver.C_adapters.Controller;
import org.sudokusolver.D_frameworksAndDrivers.FxView;


public class Main /* extends Application */ {


    private static final Logger log = LoggerFactory.getLogger(Main.class);
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/sudokusolver/sudoku-view.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root, 600, 600);
//        primaryStage.setTitle("Sudoku Solver");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

//    @Override
//    public void start(Stage primaryStage) {
//        // Eine CellView anlegen
//        CellView cell = new CellView();
//
//        // Beispiel 1: Fester Wert
//        // cell.setValue(5);
//
//        // Beispiel 2: Noch kein fester Wert, aber mögliche Kandidaten
//        cell.setValue(null);
//        cell.setPossibles(Set.of(1, 2, 5, 9));
//
//        // Container, in den wir die Zelle legen
//        StackPane root = new StackPane(cell);
//
//        // Szene erstellen und im Stage anzeigen
//        Scene scene = new Scene(root, 200, 200);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("CellView Test");
//        primaryStage.show();
//    }


    public static void main(String[] args) {
        FxView.main();
    }



}
