package denis_lebedev.pairtrading.logic;

import java.util.Calendar;


public class Quote {
    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar date;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double price;
}
