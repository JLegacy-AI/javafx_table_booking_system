module javafx.table.booking.system {
    requires java.sql;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens application;
    opens application.view;
    opens application.dbconnection;
    opens application.controller;
}