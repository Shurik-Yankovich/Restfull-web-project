package bookstore.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Bookshelf implements Serializable {

    private int id;
    private Book book;
    private int count;
    private double price;
    private LocalDate arrivalDate;

    public Bookshelf(String title, String author, int publicationYear, int count, double price, LocalDate arrivalDate) {
        this.book = new Book();
        this.book.setTitle(title);
        this.book.setAuthor(author);
        this.book.setPublicationYear(publicationYear);
        this.count = count;
        this.price = price;
        this.arrivalDate = arrivalDate;
    }

    public Bookshelf(Book book, int count, double price, LocalDate arrivalDate) {
        this.book = book;
        this.count = count;
        this.price = price;
        this.arrivalDate = arrivalDate;
    }

    public Bookshelf() {
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setBook(Book book) {
        this.book = book;
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
