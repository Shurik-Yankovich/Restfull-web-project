package bookstore.factory;

import bookstore.entity.book.Book;
import bookstore.entity.book.TechnicalBook;

public class TechnicalBookMaker implements BookMaker {
    @Override
    public Book makeBook() {
        return new TechnicalBook();
    }
}
