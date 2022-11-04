package application.model;

public class Table {
    int id;
    String table;

    //Constructor

    public Table() {
    }

    public Table(int id, String table) {
        this.id = id;
        this.table = table;
    }

    //Getter Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return table;
    }
}
