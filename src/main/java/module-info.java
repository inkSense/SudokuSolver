module org.sudokusolver {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j.core;
    requires org.slf4j;
    requires com.google.gson;
    requires java.desktop;

    opens org.sudokusolver.C_adapters to javafx.fxml, javafx.graphics;
    opens org.sudokusolver.D_frameworksAndDrivers to javafx.fxml, javafx.graphics;

    exports org.sudokusolver.B_useCases;
    exports org.sudokusolver.C_adapters;
    exports org.sudokusolver.D_frameworksAndDrivers;

}
