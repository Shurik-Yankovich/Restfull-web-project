package electronicbookstore.storage;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

    @Override
    public Bookshelf getBookshelf(Book book) {
        int index = searchBook(book);
        return bookshelves[index];
    }

    @Override
    public Bookshelf[] getBookshelfList() {
        return bookshelves;
    }

    @Override
    public Bookshelf[] getUnsoldBookshelfList() {
        Calendar unsoldDate = new GregorianCalendar();
        unsoldDate.add(Calendar.MONTH, -6);
        return getBooksByArrivalDate(unsoldDate);
    }

    private Bookshelf[] getBooksByArrivalDate(Calendar arrivalDate) {
        Bookshelf[] booksByArrivalDate = new Bookshelf[0];
        int index;
        for (Bookshelf bookshelf: bookshelves){
            if (bookshelf.getArrivalDate().compareTo(arrivalDate) != 1) {
                index = booksByArrivalDate.length;
                booksByArrivalDate = Arrays.copyOf(booksByArrivalDate, index + 1);
                booksByArrivalDate[index] = bookshelf;
            }
        }
        return booksByArrivalDate;
    }

    @Override
    public String getBookDescription(Book book) {
        int index = searchBook(book);
        return bookshelves[index].toString();
    }

    @Override
    public void takeOutBook(Book book) {
        int index = searchBook(book);
        bookshelves[index].setPresence(false);
    }

    private int searchBook(Book book) {
        for (int i = 0; i < bookshelves.length; i++) {
            if (bookshelves[i].getBook().equals(book)) {
                return i;
            }
        }
        return -1;
    }
}
