package electronicbookstore.service.storage;

import electronicbookstore.model.Book;
import electronicbookstore.model.Bookshelf;

public interface Storage {

    void changePresence(Book book, boolean presence);
    void comingBook(Book book);
    Bookshelf getBookshelf(Book book);
    Bookshelf[] getBookshelfList();
    Bookshelf[] getUnsoldBookshelfList();
    String getBookDescription(Book book);
    void takeOutBook(Book book);

}
