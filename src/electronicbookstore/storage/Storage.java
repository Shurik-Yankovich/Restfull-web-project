package electronicbookstore.storage;

public interface Storage {

    void changePresence(Book book, boolean presence);
    Bookshelf[] getBookshelfList();
    Bookshelf[] getUnsoldBookshelfList();
    String getBookDescription(Book book);

}
