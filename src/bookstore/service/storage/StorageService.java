package bookstore.service.storage;

import bookstore.model.Order;
import bookstore.model.book.Book;
import bookstore.model.Bookshelf;

import java.util.List;

public interface StorageService {

    void addBookOnStorage(Book book, int count);
    double getTotalPrice(List<Book> books);
    List<Book> checkBooksNotInStorage(List<Book> books);
    void cancelBookReservation(Order order);
    List<Bookshelf> getBookshelfList();
    List<Bookshelf> getSortingBookshelves();
    List<Bookshelf> getUnsoldBookshelves();
    void exportData();
    void importData();
}
