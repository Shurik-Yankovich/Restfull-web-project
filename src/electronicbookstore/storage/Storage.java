package electronicbookstore.storage;

public interface Storage {

    void changePresence(Book book, boolean presence);
    Bookshelf getBookshelf(Book book);
    Bookshelf[] getBookshelfList();
    Bookshelf[] getUnsoldBookshelfList();
    String getBookDescription(Book book);
    void takeOutBook(Book book);

}
