module com.mycompany.systemsoftcw {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.systemsoftcw to javafx.fxml;
    exports com.mycompany.systemsoftcw;
}
