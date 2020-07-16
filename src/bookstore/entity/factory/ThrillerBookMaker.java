package bookstore.entity.factory;

import bookstore.entity.book.Book;
import bookstore.entity.book.ThrillerBook;

public class ThrillerBookMaker implements BookMaker {
    @Override
    public Book makeBook() {
        return new ThrillerBook();
    }
}
