module com.example.battleships_client_gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.battleships_client_gui to javafx.fxml;
    exports com.example.battleships_client_gui;
}