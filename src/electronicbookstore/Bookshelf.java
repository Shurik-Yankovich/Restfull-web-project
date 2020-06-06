package electronicbookstore;

import java.util.Calendar;

public class Bookshelf {

    private Book book;
    private boolean presence;
    private double price;
    private Calendar arrivalDate;

    public Bookshelf(Book book, double price, Calendar arrivalDate) {
        this.book = book;
        this.presence = true;
        this.price = price;
        this.arrivalDate = arrivalDate;
    }

    public Book getBook() {
        return book;
    }

    public boolean isPresence() {
        return presence;
    }

    public double getPrice() {
        return price;
    }

    public Calendar getArrivalDate() {
        return arrivalDate;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setArrivalDate(Calendar arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
