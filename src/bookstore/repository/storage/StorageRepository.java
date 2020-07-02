package bookstore.repository.storage;

import bookstore.model.Bookshelf;
import bookstore.model.book.Book;
import bookstore.repository.Repository;

public interface StorageRepository extends Repository<Bookshelf, Integer, Book, Book> {
}
