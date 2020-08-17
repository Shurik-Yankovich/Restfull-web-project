package bookstore.entity;

import bookstore.entity.book.Book;

import java.io.Serializable;

import static bookstore.entity.Status.NEW;

public class Request implements Serializable {

    private int id;
    private Book book;
    private int count;
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
