package electronicbookstore.service.storage;

import electronicbookstore.util.comparator.*;
import electronicbookstore.model.Book;
import electronicbookstore.model.Bookshelf;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class BookStorage implements Storage {

    private static final String BOOK_NOT_FOUND = "Book not found!";

    private Bookshelf[] bookshelves;

    public BookStorage(Bookshelf[] bookshelves) {
        this.bookshelves = bookshelves;
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
    public void comingBook(Book book) {
        int index = searchBook(book);
        if (index >= 0) {
            bookshelves[index].setArrivalDate(new GregorianCalendar());
            bookshelves[index].setPresence(true);
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
        return sortBookshelves();
    }

    @Override
    public Bookshelf[] getUnsoldBookshelfList() {
        Calendar unsoldDate = new GregorianCalendar();
        unsoldDate.add(Calendar.MONTH, -6);
        Bookshelf[] books = getBooksByArrivalDate(unsoldDate);
        sortUnsoldBook(books);
        return books;
    }

    private Bookshelf[] getBooksByArrivalDate(Calendar arrivalDate) {
        Bookshelf[] booksByArrivalDate = new Bookshelf[0];
        int index;
        for (Bookshelf bookshelf: bookshelves){
            if (bookshelf.getArrivalDate().compareTo(arrivalDate) < 0 && bookshelf.isPresence()) {
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

    private Bookshelf[] sortBookshelves(){
        Comparator<Bookshelf> bookComp = new BookshelfTitleComparator().thenComparing(new BookshelfPublicationYearComparator())
                .thenComparing(new BookshelfPriceComparator()).thenComparing(new BookshelfPresenceComparator());
        Bookshelf[] books = Arrays.copyOf(bookshelves, bookshelves.length);
        Arrays.sort(books, bookComp);
        return books;
    }

    private void sortUnsoldBook(Bookshelf[] books){
        Comparator<Bookshelf> bookComp = new BookshelfArrivalDateComparator().thenComparing(new BookshelfPriceComparator());
        Arrays.sort(books, bookComp);
    }

    private void sortBookTitle (Bookshelf[] books) {
        Arrays.sort(books, new BookshelfTitleComparator());
    }

    private void sortPublicationYear (Bookshelf[] books) {
        Arrays.sort(books, new BookshelfPublicationYearComparator());
    }

    private void sortArrivalDate (Bookshelf[] books) {
        Arrays.sort(books, new BookshelfArrivalDateComparator());
    }

    private void sortPrice (Bookshelf[] books) {
        Arrays.sort(books, new BookshelfPriceComparator());
    }

    private void sortPresence (Bookshelf[] books) {
        Arrays.sort(books, new BookshelfPresenceComparator());
    }

}
