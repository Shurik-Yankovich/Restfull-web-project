package bookstore.model;

import bookstore.model.book.Book;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Bookshelf {

    private Book book;
    private int count;
    private double price;
    private LocalDate arrivalDate;

    public Bookshelf(Book book, int count, double price, LocalDate arrivalDate) {
        this.book = book;
        this.count = count;
        this.price = price;
        this.arrivalDate = arrivalDate;
    }

    public Book getBook() {
        return book;
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @Override
    public String toString() {
        return book.toString() + ", count=" + count +
                ", price=" + price +
                ", arrivalDate=" + arrivalDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) + "\n";
    }
}
