package bookstore.repository.list;

import bookstore.entity.Bookshelf;
import bookstore.entity.book.Book;
import bookstore.repository.base.StorageRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookStorageRepository implements StorageRepository {

    private static final String BOOK_NOT_FOUND = "Book not found!";

    private List<Bookshelf> bookshelves;
    private int index;

    public BookStorageRepository() {
        bookshelves = new ArrayList<>();
        index = 0;
    }

    @Override
    public Bookshelf create(Bookshelf book) {
        book.setId(index);
        index++;
        bookshelves.add(book);
        return book;
    }

    @Override
    public Bookshelf read(Book book) {
        int index = searchBook(book);
        return bookshelves.get(index);
    }

    @Override
    public void delete(Book book) {
        int index = searchBook(book);
        bookshelves.remove(index);
    }

    @Override
    public Bookshelf update(Book book, Integer count) {
        int index = searchBook(book);
        Bookshelf bookshelf = null;
        if (index >= 0) {
            bookshelf = bookshelves.get(index);
            int currentCount = bookshelf.getCount();
            bookshelf.setArrivalDate(LocalDate.now());
            bookshelf.setCount(count + currentCount);
        } else {
            System.out.println(BOOK_NOT_FOUND);
        }
        return bookshelf;
    }

    @Override
    public List<Bookshelf> readAll() {
        return bookshelves;
    }

    @Override
    public void createAll(List<Bookshelf> bookshelves) {
        this.bookshelves = bookshelves;
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