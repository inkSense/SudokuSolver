package org.sudokusolver.D_frameworksAndDrivers;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.C_adapters.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SudokuListWindow {

    private Stage stage;
    private ListView<String> listView = new ListView<>();
    private Button loadButton = new Button("Load");

    private static final Logger log = LoggerFactory.getLogger(SudokuListWindow.class);

    Controller controller;

    public SudokuListWindow(Controller controller) {
        this.controller = controller;
        List<String> fileNames = getFileNames(FrameworkConf.filePathString);
        listView.getItems().addAll(fileNames);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        loadButton.setOnAction(e -> {
            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Path filePath = Path.of(FrameworkConf.filePathString + "/" + selected);
                controller.loadSudoku(filePath);
                log.info("filePath: " + filePath);
                stage.close();
            }
        });

        BorderPane root = new BorderPane();
        root.setCenter(listView);
        root.setBottom(loadButton);

        Scene scene = new Scene(root, 300, 200);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Sudoku-Auswahl");
    }

    public static List<String> getFileNames(String dirPath) {
        try (var paths = Files.list(Paths.get(dirPath))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch(IOException e){
            log.error("Wrong in getFileNames()");
            return new ArrayList<>();
        }
    }

    public void show() {
        stage.show();
    }


}
