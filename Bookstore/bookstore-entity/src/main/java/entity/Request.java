package entity;

//import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.io.Serializable;

import static entity.Status.NEW;

@Entity
@Table(name = "Book_Request")
public class Request implements Serializable {

    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "count")
    private int count;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public Request(Book book) {
        this.book = book;
        this.count = 1;
        this.status = NEW;
    }

    public Request() {
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

    public Status getStatus() {
        return status;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request:" + book +
                ", status - " + status + '\n';
    }
}
