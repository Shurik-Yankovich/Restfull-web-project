package bookstore.repository.storage;

import bookstore.model.Book;
import bookstore.model.Bookshelf;

import java.util.List;

public interface Storage {

    void comingBook(Book book, int count);
    Bookshelf getBookshelf(Book book);
    List<Bookshelf> getBookshelfList();
    List<Bookshelf> getUnsoldBookshelfList();
    boolean takeOutBook(Book book);

}
