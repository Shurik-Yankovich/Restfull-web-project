package electronicbookstore;

import static electronicbookstore.Status.NEW;

public class BookRequest {

    private Book book;
    private Status status;

    public BookRequest(Book book) {
        this.book = book;
        this.status = NEW;
    }

    public Book getBook() {
        return book;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
