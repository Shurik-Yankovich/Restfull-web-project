package electronicbookstore.storage;

public class BookStorage implements Storage {

    private static final String BOOK_NOT_FOUND = "Данной книги нет в списке!";

    private Bookshelf[] bookshelves;

    public BookStorage(Bookshelf[] bookshelves) {
        this.bookshelves = bookshelves;
    }

    public BookStorage() {
    }

    @Override
    public void changePresence(Book book, boolean presence) {
        int index = searchBook(book);
        if (index >= 0) {
            bookshelves[index].setPresence(presence);
        } else {
            System.out.println(BOOK_NOT_FOUND);
        }
    }

    private int searchBook(Book book) {
        for (int i = 0; i < bookshelves.length; i++) {
            if (bookshelves[i].getBook().equals(book)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Bookshelf[] getBookshelfList() {
        return bookshelves;
    }

    @Override
    public Bookshelf[] getUnsoldBookshelfList() {
        return new Bookshelf[0];
    }

    @Override
    public String getBookDescription(Book book) {
        return null;
    }
}
