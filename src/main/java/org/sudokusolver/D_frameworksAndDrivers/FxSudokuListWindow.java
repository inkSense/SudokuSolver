package org.sudokusolver.D_frameworksAndDrivers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.C_adapters.Controller;
import java.util.List;

public class FxSudokuListWindow {

    private final Stage stage;
    private final ListView<String> listView = new ListView<>();
    private final TextField fileNameField = new TextField();
    private static final Logger log = LoggerFactory.getLogger(FxSudokuListWindow.class);
    private final Controller controller;

    public FxSudokuListWindow(Controller controller, ListWindowMode mode, String defaultFileName) {
        this.controller = controller;
        List<String> fileNames = controller.getAllDataFileNames();
        listView.getItems().addAll(fileNames);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        BorderPane root = new BorderPane();
        root.setCenter(listView);

        listView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> fileNameField.setText(newV == null ? "" : newV)
        );

        if(ListWindowMode.load == mode){
            Button loadButton = new Button("Load");
            setLoadButtonAction(loadButton);
            root.setBottom(loadButton);
        } else if(ListWindowMode.save == mode){
            Button saveButton = new Button("Save");
            setSaveButtonAction(saveButton);

            fileNameField.setPromptText("Dateiname eingeben …");

            // defaultFileName vorbelegen & Liste selektieren
            if (defaultFileName != null && !defaultFileName.isBlank()) {
                fileNameField.setText(defaultFileName);
                listView.getSelectionModel().select(defaultFileName);
            }
            HBox bottom = new HBox(10, fileNameField, saveButton);
            bottom.setAlignment(Pos.CENTER_RIGHT);
            bottom.setPadding(new Insets(10));
            root.setBottom(bottom);
        } else {
            log.error("Enum not registered in FxSudokuListWindow");
        }

        Scene scene = new Scene(root, FrameworkConf.listSceneX, FrameworkConf.listSceneY);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Sudoku-Auswahl");
    }

    void show() {
        stage.show();
    }

    private void setLoadButtonAction(Button loadButton){
        loadButton.setOnAction(e -> {
            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                controller.loadSudoku(selected);
                log.info("file: " + selected);
                stage.close();
            }
        });
    }

    private void setSaveButtonAction(Button saveButton) {
        saveButton.setOnAction(e -> {
            String fileName = fileNameField.getText().trim();
            if (fileName.isBlank()) {
                log.warn("Kein Dateiname angegeben.");
                return;
            }
            controller.saveSudoku(fileName);
            stage.close();
        });
    }

}
