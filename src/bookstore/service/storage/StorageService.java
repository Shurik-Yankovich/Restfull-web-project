package bookstore.service.storage;

import bookstore.entity.Order;
import bookstore.entity.book.Book;
import bookstore.entity.Bookshelf;

import java.util.List;

public interface StorageService {

    Bookshelf addBookOnStorage(Book book, int count);
    Bookshelf addBookOnStorage(Book book, int count, double price);
    double getTotalPrice(List<Book> books);
    List<Book> checkBooksNotInStorage(List<Book> books);
    void cancelBookReservation(Order order);
    List<Bookshelf> getBookshelfList();
    List<Bookshelf> getSortingBookshelves();
    List<Bookshelf> getUnsoldBookshelves();
    void readAllFromFile();
    void writeAllToFile();
    void writeBookshelfToFile(Bookshelf bookshelf);
    void updateBookshelfToFile(Bookshelf bookshelf);
}
