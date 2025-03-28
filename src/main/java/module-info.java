module org.sudokusolver {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j.core;
    requires org.slf4j;
    requires com.google.gson;

    opens org.sudokusolver.D_frameworksAndDrivers to javafx.fxml;

    exports org.sudokusolver;
    exports org.sudokusolver.A_entities.objectsAndDataStructures;
    exports org.sudokusolver.C_adapters;
}
