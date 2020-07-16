package bookstore.entity.factory;

import bookstore.entity.book.Book;
import bookstore.entity.book.NovelBook;

public class NovelBookMaker implements BookMaker {
    @Override
    public Book makeBook() {
        return new NovelBook();
    }
}
