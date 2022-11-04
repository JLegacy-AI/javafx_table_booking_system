package application.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import application.StartApplication;
import application.dbconnection.DatabaseConnector;
import application.model.Booking;
import application.model.Table;
import application.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextField emailTextField;
    public TextField passwordTextField;

    public ScrollPane requestsScrollPane;
    public ComboBox<Object> tableComboBox;
    public DatePicker datePicker;
    public ScrollPane approvedScrollPane;
    public ScrollPane slotsScrollPane;
    public ComboBox staffComboBox;
    public ScrollPane staffScrollPane;

    private static User user = new User(-1,".",".",".",null);
    private Table selectedTable;

    public static Connection connection;
    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/table_booking","root","Mr___Jexness5@");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void handleSigninButton(ActionEvent actionEvent) {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        this.user = DatabaseConnector.getUser(email, password);
        if(this.user != null){
            if(user.getType() == User.UserType.USER){
//                System.out.println("User LogIn");
                System.out.println(user);
                Parent root = null;
                try {
                    root = FXMLLoader.load(StartApplication.class.getResource("view/BookerView.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 600,400));
                stage.setTitle("User Panel");
                stage.setResizable(false);
                stage.show();
            }
            if(user.getType() == User.UserType.ADMIN){
                System.out.println("ADMIN LogIn");
                Parent root = null;
                try {
                    root = FXMLLoader.load(StartApplication.class.getResource("view/AdminUI.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 600,400));
                stage.setTitle("Admin Panel");
                stage.setResizable(false);
                stage.show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] urlParts = url.getFile().split("/");
        if(urlParts[urlParts.length-1].equals("BookerView.fxml")){
            System.out.println("Booker UI");
            tableComboBox.setItems(FXCollections.observableArrayList(DatabaseConnector.getTables()));
            datePicker.setDisable(true);
        }else if(urlParts[urlParts.length-1].equals("AdminUI.fxml")){
            System.out.println("Admin UI");
            setApprovedScrollPane();
            setRequestScrollPane();
        }
    }


    public void handleTableComboBox(ActionEvent actionEvent) {
        selectedTable =(Table) ((ComboBox)actionEvent.getSource()).getValue();
        if(selectedTable!=null){
            datePicker.setDisable(false);
        }
    }

    public void addSlots(){
        if(!datePicker.isDisable()){
            System.out.println("1");
            if(datePicker.getValue()!=null){
                System.out.println("2");
                VBox vb = new VBox();
                vb.setSpacing(10);
                for (int i = 1; i <= 24 ; i++) {
                    HBox hbox = new HBox();
                    hbox.setPrefHeight(20);
                    hbox.setSpacing(30);
                    hbox.setPadding(new Insets(10,10,10,10));

                    Booking booking = DatabaseConnector.getBookingByTableIDByDate(selectedTable.getId(),getDateFormat(datePicker.getValue()), i);
                    Text dateText = new Text("id:"+i+"      "+getDateFormat(datePicker.getValue())+"      Time: "+i);
                    dateText.setStyle("-fx-font-size: 14px;");
                    hbox.getChildren().add(dateText);
                    if( booking == null){
                        Button btn = new Button("Book:"+i);
                        hbox.setStyle("-fx-background-color: green;");
                        hbox.getChildren().add(btn);
                        btn.setOnAction(this::handleSlotBookButton);
                    }else{
                        if(booking.getStatus()== Booking.BookingStatus.APPROVED){
                            hbox.setStyle("-fx-background-color: red");
                        }else if(booking.getStatus() == Booking.BookingStatus.PENDING){
                            hbox.setStyle("-fx-background-color: yellow");
                        }
                    }

                    vb.getChildren().add(hbox);
                }
                slotsScrollPane.setContent(vb);
            }
        }
    }

    public void handleSlotBookButton(ActionEvent actionEvent){
        int time = Integer.parseInt(((Button)actionEvent.getSource()).getText().split(":")[1]);
        System.out.println(DatabaseConnector.insertBooking(selectedTable.getId(), user.getId(), getDateFormat(datePicker.getValue()), time, Booking.BookingStatus.PENDING.toString()));
        addSlots();
    }

    public void handleDateOicker(ActionEvent actionEvent) {
        addSlots();
        System.out.println(((DatePicker)actionEvent.getSource()).getValue());
    }

    public String getDateFormat(LocalDate localDate){
        return localDate.getDayOfMonth()+"-"+localDate.getMonth().toString().substring(0,3);
    }

    //Admin Panel Additional Function
    public void setRequestScrollPane(){
        ArrayList<Booking> booking = DatabaseConnector.getBooking();
        VBox vb = new VBox();
        vb.setSpacing(10);

        for (Booking booking_ : booking) {
            if(booking_.getStatus() == Booking.BookingStatus.PENDING){
                HBox hb = new HBox();
                hb.setPrefHeight(20);
                hb.setSpacing(30);
                hb.setPadding(new Insets(10,10,10,10));

                hb.getChildren().add(new Text(booking_.detail()));
                Button btn = new Button("Approved:");
                hb.setStyle("-fx-background-color: green;");
                hb.getChildren().add(btn);
                btn.setOnAction(e -> {
                    DatabaseConnector.approved(booking_);
                    setRequestScrollPane();
                });
                vb.getChildren().add(hb);
            }
        }
        requestsScrollPane.setContent(vb);
    }

    public void setApprovedScrollPane(){
        ArrayList<Booking> booking = DatabaseConnector.getBooking();
        VBox vb = new VBox();
        vb.setSpacing(10);

        for (Booking booking_ : booking) {
            if(booking_.getStatus() == Booking.BookingStatus.APPROVED){
                HBox hb = new HBox();
                hb.setPrefHeight(20);
                hb.setSpacing(30);
                hb.setPadding(new Insets(10,10,10,10));

                hb.getChildren().add(new Text(booking_.detail()));
                hb.setStyle("-fx-background-color: red;");
                vb.getChildren().add(hb);
            }
        }
        approvedScrollPane.setContent(vb);
    }

}
