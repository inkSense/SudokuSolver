package org.sudokusolver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
import org.sudokusolver.D_frameworksAndDrivers.ListWindowMode;

public class Main  extends Application {


    private static final Logger log = LoggerFactory.getLogger(Main.class);


    @Override
    public void start(Stage primaryStage){
        BorderPane mainPane = new BorderPane();
        GridPane gridPane = new GridPane();

        mainPane.setCenter(gridPane);

        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku Solver");
        primaryStage.show();

        var httpGateway = new HttpApiGateway();
        var filesystemGateway = new FilesystemGateway();
        var interactor = new UseCaseInteractor(httpGateway, filesystemGateway);
        var controller = new Controller(interactor);

        // MVP
        ViewModel model = new ViewModel();
        FxView fxView = new FxView(gridPane, scene, controller);
        var presenter = new Presenter(model, fxView);
        controller.setPresenter(presenter);
        /*
        Das View hat den Controller.
        Der Controller hat den Presenter und den UseCaseInteractor.
        Der Presenter hat das Model und das View.
        Das Model hat nichts. */

        Button loadBtn     = fxView.onButtonClickOpenList(ListWindowMode.load);
        Button saveButton  = fxView.onButtonClickOpenList(ListWindowMode.save);
        Button downloadBtn = fxView.onButtonClickDownload();

        VBox leftBar = new VBox(10, loadBtn, saveButton, downloadBtn);
        mainPane.setLeft(leftBar);

    }

    public static void main(String[] args) {
        launch(args);
    }



}
