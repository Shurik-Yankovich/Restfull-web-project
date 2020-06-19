package bookstore.service.storage;

import bookstore.model.Book;
import bookstore.model.Bookshelf;

import java.util.List;

public interface StorageService {

    void addBookOnStorage(Book book, int count);
    double getTotalPrice(List<Book> books);
    List<Book> checkBooksNotOnStorage(List<Book> books);
    List<Bookshelf> getBookList();
    List<Bookshelf> getSortingBookList();
    List<Bookshelf> getUnsoldBookList();
}
