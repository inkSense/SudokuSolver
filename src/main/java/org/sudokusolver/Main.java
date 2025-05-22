package org.sudokusolver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

public class Main extends Application {


    private static final Logger log = LoggerFactory.getLogger(Main.class);


    @Override
    public void start(Stage stage){
//        BorderPane mainPane = new BorderPane();
//        GridPane gridPane = new GridPane();
//        Scene scene = new Scene(mainPane, 800, 600);

        var httpGateway = new HttpApiGateway();
        var filesystemGateway = new FilesystemGateway();
        var interactor = new UseCaseInteractor(httpGateway, filesystemGateway);
        var controller = new Controller(interactor);

        // MVP
        ViewModel model = new ViewModel();
        FxView fxView = new FxView(stage, controller);
        var presenter = new Presenter(model, fxView);
        controller.setPresenter(presenter);
        /*
        Das View hat den Controller.
        Der Controller hat den Presenter und den UseCaseInteractor.
        Der Presenter hat das Model und das View.
        Das Model hat nichts. */

//        primaryStage.setScene(scene);
//        stage.setTitle("Sudoku Solver");
//        stage.show();
//        mainPane.setCenter(gridPane);
//        mainPane.setLeft(fxView.getButtonsInVBox());
    }

    public static void main(String[] args) {
        launch(args);
    }



}
