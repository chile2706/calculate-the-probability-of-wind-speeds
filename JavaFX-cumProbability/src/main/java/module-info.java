module com.example.javafxcumprobability {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.javafxcumprobability to javafx.fxml;
    exports com.example.javafxcumprobability;
}