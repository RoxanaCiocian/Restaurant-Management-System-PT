package Model;

import java.util.Objects;

public class Order {
    private int OrderID;
    private Date date;
    private int Table;

    public Order(int orderID, Date date, int table) {
        this.OrderID = orderID;
        this.date = date;
        this.Table = table;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        date = date;
    }

    public int getTable() {
        return Table;
    }

    public void setTable(int table) {
        Table = table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return this.OrderID == order.OrderID &&
                this.Table == order.Table &&
                this.date.getDay() == order.date.getDay();

    }

    @Override
    public int hashCode() {
        return Objects.hash(OrderID, date, Table);
    }

    @Override
    public String toString() {
        return "Order{" +
                "OrderID=" + OrderID +
                ", date=" + date +
                ", Table=" + Table +
                '}';
    }
}
