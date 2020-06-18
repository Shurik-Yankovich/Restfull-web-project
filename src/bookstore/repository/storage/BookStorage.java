package bookstore.repository.storage;

import bookstore.model.Book;
import bookstore.model.Bookshelf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void comingBook(Book book, int count) {
        int index = searchBook(book);
        if (index >= 0) {
            Bookshelf bookshelf = bookshelves.get(index);
            int currentCount = bookshelf.getCount();
            bookshelf.setArrivalDate(LocalDate.now());
            bookshelf.setCount(count + currentCount);
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
        return bookshelves;
    }

    @Override
    public List<Bookshelf> getUnsoldBookshelfList() {
        LocalDate unsoldDate = LocalDate.now().minusMonths(NUMBER_OF_MONTHS_FOR_UNSOLD_BOOKS);
        return getBooksBeforeArrivalDate(unsoldDate);
    }

    private List<Bookshelf> getBooksBeforeArrivalDate(LocalDate arrivalDate) {
        List<Bookshelf> booksBeforeArrivalDate = new ArrayList<>();
        for (Bookshelf bookshelf : bookshelves) {
            if (bookshelf.getArrivalDate().isBefore(arrivalDate) && bookshelf.getCount() > 0) {
                booksBeforeArrivalDate.add(bookshelf);
            }
        }
        return booksBeforeArrivalDate;
    }

    @Override
    public boolean takeOutBook(Book book) {
        int index = searchBook(book);
        if (index >= 0) {
            Bookshelf bookshelf = bookshelves.get(index);
            int count = bookshelf.getCount();
            if (count > 0) {
                bookshelf.setCount(count - 1);
                return true;
            }
        }
        return false;
    }

    private int searchBook(Book book) {
        for (int i = 0; i < bookshelves.size(); i++) {
            if (bookshelves.get(i).getBook().equals(book)) {
                return i;
            }
        }
        return -1;
    }
}
