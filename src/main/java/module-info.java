module org.education.hospitalmanagementapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.education.hospitalmanagementapp to javafx.fxml;
    exports org.education.hospitalmanagementapp;
}