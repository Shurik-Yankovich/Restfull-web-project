package bookstore.repository.base;

import bookstore.entity.Bookshelf;
import bookstore.entity.book.Book;

public interface StorageRepository extends Repository<Bookshelf, Integer, Book, Book> {
}
