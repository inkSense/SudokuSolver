module org.sudokusolver {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j.core;
    requires org.slf4j;
    requires com.google.gson;
    requires java.desktop;


    opens org.sudokusolver.D_frameworksAndDrivers to javafx.fxml, javafx.graphics;

    // --- Exportierte Module ---
    exports org.sudokusolver;
    exports org.sudokusolver.A_entities.objects;
    exports org.sudokusolver.C_adapters;
    opens org.sudokusolver.C_adapters to javafx.fxml, javafx.graphics;
    exports org.sudokusolver.D_frameworksAndDrivers;
    exports org.sudokusolver.A_entities.dataStructures;
}
