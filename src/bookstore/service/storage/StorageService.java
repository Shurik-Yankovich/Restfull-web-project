package bookstore.service.storage;

import bookstore.model.book.Book;
import bookstore.model.Bookshelf;

import java.util.List;

public interface StorageService {

    void addBookOnStorage(Book book, int count);
    double getTotalPrice(List<Book> books);
    List<Book> checkBooksNotOnStorage(List<Book> books);
    List<Bookshelf> getAll();
    List<Bookshelf> getSortingBookshelves();
    List<Bookshelf> getUnsoldBookshelves();
}
