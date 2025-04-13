package org.sudokusolver;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.B_useCases.UseCaseInteractor;
import org.sudokusolver.C_adapters.Controller;
import org.sudokusolver.C_adapters.FilesystemGateway;
import org.sudokusolver.C_adapters.HttpApiGateway;
import org.sudokusolver.C_adapters.ViewModel;
import org.sudokusolver.C_adapters.Presenter;
import org.sudokusolver.D_frameworksAndDrivers.FxView;


public class Main  extends Application {


    private static final Logger log = LoggerFactory.getLogger(Main.class);


    @Override
    public void start(Stage primaryStage){
        // Container, in den wir das Grid legen
        GridPane gridPane = new GridPane();
        StackPane rootPane = new StackPane(gridPane);
        Scene scene = new Scene(rootPane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku Solver");
        primaryStage.show();

        var httpGateway = new HttpApiGateway();
        var filesystemGateway = new FilesystemGateway();
        var interactor = new UseCaseInteractor(httpGateway, filesystemGateway);
        var controller = new Controller(interactor);

        // MVP
        ViewModel model = new ViewModel(controller);
        FxView fxView = new FxView(gridPane, scene);
        new Presenter(model, fxView);

    }

    public static void main(String[] args) {
        launch(args);
    }



}
