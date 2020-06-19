package bookstore.repository.storage;

import bookstore.model.Book;
import bookstore.model.Bookshelf;

import java.util.List;

public interface StorageRepository {

    void addBook(Book book, int count);
    Bookshelf getBookshelf(Book book);
    List<Bookshelf> getAll();
    List<Bookshelf> getUnsoldBookshelves();
    boolean takeOutBook(Book book);

}
