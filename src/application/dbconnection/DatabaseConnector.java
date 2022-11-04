package application.dbconnection;

import application.controller.Controller;
import application.model.Booking;
import application.model.Table;
import application.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseConnector {

    public static ArrayList<Table> getTables(){
        try {
            Statement stmt = Controller.connection.createStatement();
            ResultSet  rs=stmt.executeQuery("SELECT * FROM table_booking.table");
            ArrayList<Table> tables = new ArrayList<>();
            while(rs.next()){
                tables.add(new Table(rs.getInt("id"), rs.getString("name")));
            }
            return tables;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Booking> getBooking(){
        try {
            Statement stmt = Controller.connection.createStatement();
            ResultSet  rs=stmt.executeQuery("SELECT * FROM booked;");
            ArrayList<Booking> bookings = new ArrayList<>();
            while(rs.next()){
                bookings.add(new Booking(
                                         rs.getInt("Table_id"),
                                         rs.getInt("User_id"),
                                         rs.getString("date"),
                                         rs.getString("time"),
                                         Booking.BookingStatus.valueOf(rs.getString("status"))
                                        )
                           );
            }
            return bookings;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static User getUser(String e, String p){
        try {
            PreparedStatement stmt = Controller.connection.prepareStatement("SELECT * FROM user WHERE email=? AND password=?;");
            stmt.setString(1, e);
            stmt.setString(2, p);
            ResultSet  rs=stmt.executeQuery();
            if(rs.next()){
                return new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        User.UserType.valueOf(rs.getString("type")));
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Booking> getBookingByTableID(int tableID){
        try {
            PreparedStatement stmt = Controller.connection.prepareStatement("SELECT * FROM booked WHERE Table_id=?;");
            stmt.setInt(1, tableID);
            ResultSet  rs=stmt.executeQuery();
            ArrayList<Booking> bookings = new ArrayList<>();
            while(rs.next()){
                bookings.add(new Booking(
                                rs.getInt("Table_id"),
                                rs.getInt("User_id"),
                                rs.getString("date"),
                                rs.getString("time"),
                                Booking.BookingStatus.valueOf(rs.getString("status"))
                        )
                );
            }
            return bookings;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static Booking getBookingByTableIDByDate(int tableID, String date, int time){
        try {
            PreparedStatement stmt = Controller.connection.prepareStatement("SELECT * FROM booked WHERE Table_id=? AND date=? AND time=?;");
            stmt.setInt(1, tableID);
            stmt.setString(2, date);
            stmt.setInt(3, time);
            ResultSet  rs=stmt.executeQuery();
            if(rs.next()){
                return new Booking(
                                rs.getInt("Table_id"),
                                rs.getInt("User_id"),
                                rs.getString("date"),
                                rs.getString("time"),
                                Booking.BookingStatus.valueOf(rs.getString("status"))
                        );
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static boolean insertBooking(int tableID, int userID ,String date, int time, String status){
        try {
            PreparedStatement stmt = Controller.connection.prepareStatement("INSERT INTO booked(Table_id, User_id, date, time, status) VALUE(?,?,?,?,?)");
            stmt.setInt(1, tableID);
            stmt.setInt(2, userID);
            stmt.setString(3, date);
            stmt.setInt(4, time);
            stmt.setString(5, status);
            stmt.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean approved(Booking booking){
        try {
            PreparedStatement pst = Controller.connection.prepareStatement("UPDATE booked SET status = ? WHERE Table_id=? AND User_id=? AND time=? AND date=?");
            pst.setString(1, Booking.BookingStatus.APPROVED.toString());
            pst.setInt(2, booking.getTableID());
            pst.setInt(3, booking.getUserID());
            pst.setString(4, booking.getTime());
            pst.setString(5, booking.getDate());
            System.out.println(pst.executeUpdate());
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

}
