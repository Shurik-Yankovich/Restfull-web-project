package electronicbookstore.service.storage;

import electronicbookstore.model.Book;
import electronicbookstore.model.Bookshelf;
import electronicbookstore.util.comparator.*;

import java.time.LocalDate;
import java.util.*;

public class BookStorage implements Storage {

    private static final String BOOK_NOT_FOUND = "Book not found!";
    private static final int NUMBER_OF_MONTHS_FOR_UNSOLD_BOOKS = 6;

    private List<Bookshelf> bookshelves;

    public BookStorage(Bookshelf... bookshelves) {
        this.bookshelves = new ArrayList<>();
        this.bookshelves = Arrays.asList(bookshelves);
    }

    public BookStorage(List<Bookshelf> bookshelves) {
        this.bookshelves = bookshelves;
    }

    @Override
    public void changePresence(Book book, boolean presence) {
        int index = searchBook(book);
        if (index >= 0) {
            bookshelves.get(index).setPresence(presence);
        } else {
            System.out.println(BOOK_NOT_FOUND);
        }
    }

    @Override
    public void comingBook(Book book) {
        int index = searchBook(book);
        if (index >= 0) {
            Bookshelf bookshelf = bookshelves.get(index);
            bookshelf.setArrivalDate(LocalDate.now());
            bookshelf.setPresence(true);
        } else {
            System.out.println(BOOK_NOT_FOUND);
        }
    }

    @Override
    public Bookshelf getBookshelf(Book book) {
        int index = searchBook(book);
        return bookshelves.get(index);
    }

    @Override
    public List<Bookshelf> getBookshelfList() {
        return sortBookshelves();
    }

    @Override
    public List<Bookshelf> getUnsoldBookshelfList() {
        LocalDate unsoldDate = LocalDate.now().minusMonths(NUMBER_OF_MONTHS_FOR_UNSOLD_BOOKS);
        List<Bookshelf> books = getBooksBeforeArrivalDate(unsoldDate);
        sortUnsoldBook(books);
        return books;
    }

    private List<Bookshelf> getBooksBeforeArrivalDate(LocalDate arrivalDate) {
        List<Bookshelf> booksBeforeArrivalDate = new ArrayList<>();
        for (Bookshelf bookshelf: bookshelves){
            if (bookshelf.getArrivalDate().isBefore(arrivalDate) && bookshelf.isPresence()) {
                booksBeforeArrivalDate.add(bookshelf);
            }
        }
        return booksBeforeArrivalDate;
    }

    @Override
    public String getBookDescription(Book book) {
        int index = searchBook(book);
        return bookshelves.get(index).toString();
    }

    @Override
    public void takeOutBook(Book book) {
        int index = searchBook(book);
        bookshelves.get(index).setPresence(false);
    }

    private int searchBook(Book book) {
        for (int i = 0; i < bookshelves.size(); i++) {
            if (bookshelves.get(i).getBook().equals(book)) {
                return i;
            }
        }
        return -1;
    }

    private List<Bookshelf> sortBookshelves(){
        Comparator<Bookshelf> bookComp = new BookshelfTitleComparator().thenComparing(new BookshelfPublicationYearComparator())
                .thenComparing(new BookshelfPriceComparator()).thenComparing(new BookshelfPresenceComparator());
        List<Bookshelf> books = new ArrayList<>(bookshelves);
        books.sort(bookComp);
        return books;
    }

    private void sortUnsoldBook(List<Bookshelf> books){
        Comparator<Bookshelf> bookComp = new BookshelfArrivalDateComparator().thenComparing(new BookshelfPriceComparator());
        books.sort(bookComp);
    }

    private void sortBookTitle (List<Bookshelf> books) {
        books.sort(new BookshelfTitleComparator());
    }

    private void sortPublicationYear (List<Bookshelf> books) {
        books.sort(new BookshelfPublicationYearComparator());
    }

    private void sortArrivalDate (List<Bookshelf> books) {
        books.sort(new BookshelfArrivalDateComparator());
    }

    private void sortPrice (List<Bookshelf> books) {
        books.sort(new BookshelfPriceComparator());
    }

    private void sortPresence (List<Bookshelf> books) {
        books.sort(new BookshelfPresenceComparator());
    }

}
