package bookstore.repository.storage;

import bookstore.model.Book;
import bookstore.model.Bookshelf;

import java.util.List;

public interface Storage {

    void changePresence(Book book, boolean presence);
    void comingBook(Book book);
    Bookshelf getBookshelf(Book book);
    List<Bookshelf> getBookshelfList();
    List<Bookshelf> getUnsoldBookshelfList();
    String getBookDescription(Book book);
    void takeOutBook(Book book);

}
