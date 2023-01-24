module com.mycompany.systemsoftcw {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.systemsoftcw to javafx.fxml;
    exports com.mycompany.systemsoftcw;
}
