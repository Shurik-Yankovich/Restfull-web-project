package electronicbookstore.store.arrays;

import electronicbookstore.storage.Book;
import electronicbookstore.store.Status;

import static electronicbookstore.store.Status.NEW;

public class Request {

    private Book book;
    private int count;
    private int number;
    private Status status;

    public Request(Book book) {
        this.book = book;
        this.count = 1;
        this.status = NEW;
    }

    public Book getBook() {
        return book;
    }

    public int getCount() {
        return count;
    }

    public int getNumber() {
        return number;
    }

    public Status getStatus() {
        return status;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setNumber(int number) {
        this.number = number;
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
