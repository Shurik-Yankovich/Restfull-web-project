package bookstore.service.storage;

import bookstore.entity.Order;
import bookstore.entity.book.Book;
import bookstore.entity.Bookshelf;
import bookstore.exeption.RepositoryException;

import java.util.List;

public interface StorageService {

    Bookshelf addBookOnStorage(Book book, int count) throws RepositoryException;

    Bookshelf addBookOnStorage(Book book, int count, double price) throws RepositoryException;

    double getTotalPrice(List<Book> books) throws RepositoryException;

    List<Book> checkBooksNotInStorage(List<Book> books) throws RepositoryException;

    void cancelBookReservation(Order order) throws RepositoryException;

    List<Bookshelf> getBookshelfList() throws RepositoryException;

    List<Bookshelf> getSortingBookshelves() throws RepositoryException;

    List<Bookshelf> getUnsoldBookshelves() throws RepositoryException;

    void readAllFromFile() throws RepositoryException;

    void writeAllToFile() throws RepositoryException;

    void writeBookshelfToFile(Bookshelf bookshelf) throws RepositoryException;

    void updateBookshelfToFile(Bookshelf bookshelf) throws RepositoryException;
}
