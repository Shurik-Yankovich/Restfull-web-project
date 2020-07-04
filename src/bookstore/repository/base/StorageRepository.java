package bookstore.repository.base;

import bookstore.model.Bookshelf;
import bookstore.model.book.Book;

public interface StorageRepository extends Repository<Bookshelf, Integer, Book, Book> {
}
