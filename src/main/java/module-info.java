module org.education.hospitalmanagementapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.education.hospitalmanagementapp to javafx.fxml;
    exports org.education.hospitalmanagementapp;
}