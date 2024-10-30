module org.education.hospitalmanagementapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.education.hospitalmanagementapp to javafx.fxml;
    exports org.education.hospitalmanagementapp;
    opens org.education.hospitalmanagementapp.controllers to  javafx.fxml;
    exports org.education.hospitalmanagementapp.controllers to  javafx.fxmls;
}