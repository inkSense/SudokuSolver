package org.sudokusolver;

import javafx.application.Application;
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
        // wiring
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

    }

    public static void main(String[] args) {
        launch(args);
    }



}
