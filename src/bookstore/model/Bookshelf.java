package bookstore.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Bookshelf {

    private Book book;
    private boolean presence;
    private double price;
    private LocalDate arrivalDate;

    public Bookshelf(Book book, double price, LocalDate arrivalDate) {
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

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @Override
    public String toString() {
        return book.toString() + ", presence=" + presence +
                ", price=" + price +
                ", arrivalDate=" + arrivalDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) + "\n";
    }
}
