package bookstore.service.storage;

import bookstore.entity.Order;
import bookstore.entity.Book;
import bookstore.entity.Bookshelf;
import bookstore.exeption.RepositoryException;

import java.util.List;

public interface StorageService {

    Bookshelf addBookOnStorage(Book book, int count);

    Bookshelf addBookOnStorage(Book book, int count, double price);

    double getTotalPrice(List<Book> books) throws RepositoryException;

    List<Book> checkBooksNotInStorage(List<Book> books);

    void cancelBookReservation(Order order) throws RepositoryException;

    Bookshelf getBookshelf(Book book);

    List<Bookshelf> getBookshelfList();

    List<Bookshelf> getSortingBookshelves();

    List<Bookshelf> getUnsoldBookshelves();

    boolean readAllFromFile();

    boolean writeAllToFile();

    boolean writeBookshelfToFile(Bookshelf bookshelf);

    boolean updateBookshelfToFile(Bookshelf bookshelf);

    boolean save();
}
