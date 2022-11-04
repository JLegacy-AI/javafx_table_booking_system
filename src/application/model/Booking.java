package application.model;

public class Booking {
    public enum BookingStatus {APPROVED, PENDING, DECLINE};

    int tableID;
    int userID;
    String date;
    String time;
    BookingStatus status;

    //Constructor

    public Booking(int tableID, int userID, String date, String time, BookingStatus status) {
        this.tableID = tableID;
        this.userID = userID;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public Booking() {}

    //Getter Setter

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String detail(){
        return "Booking:    table_id: "+tableID+"     User_id:"+userID+"     Date: "+date+"     Time:"+time+"     Status:"+status.toString();
    }

    @Override
    public String toString() {
        return time;
    }
}
