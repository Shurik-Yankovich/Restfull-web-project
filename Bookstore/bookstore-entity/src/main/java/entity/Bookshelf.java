package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Bookshelf")
public class Bookshelf implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "count")
    private int count;
    @Column(name = "price")
    private double price;
    @Column(name = "arrival_date")
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
