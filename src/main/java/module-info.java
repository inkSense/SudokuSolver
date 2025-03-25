module org.sudokusolver {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.sudokusolver to javafx.fxml;
    exports org.sudokusolver;
}