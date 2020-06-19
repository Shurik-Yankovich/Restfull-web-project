package bookstore.service.storage;

import bookstore.model.Book;
import bookstore.model.Bookshelf;
import bookstore.repository.storage.StorageRepository;
import bookstore.util.comparator.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookStorageService implements StorageService {

    private StorageRepository bookStorageRepository;

    public BookStorageService(StorageRepository bookStorageRepository) {
        this.bookStorageRepository = bookStorageRepository;
    }

    @Override
    public void addBookOnStorage(Book book, int count) {
        bookStorageRepository.addBook(book, count);
    }

    public double getTotalPrice(List<Book> books) {
        double price = 0;
        for (Book book : books) {
            price += bookStorageRepository.getBookshelf(book).getPrice();
        }
        return price;
    }

    public List<Book> checkBooksNotOnStorage(List<Book> books) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (!bookStorageRepository.takeOutBook(book)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public List<Bookshelf> getAll() {
        return bookStorageRepository.getAll();
    }

    @Override
    public List<Bookshelf> getSortingBookshelves() {
        List<Bookshelf> books = new ArrayList<>(bookStorageRepository.getAll());
        if (books.size() > 0) {
            Comparator<Bookshelf> bookComp = new BookshelfTitleComparator().thenComparing(new BookshelfPublicationYearComparator())
                    .thenComparing(new BookshelfPriceComparator()).thenComparing(new BookshelfPresenceComparator());
            books.sort(bookComp);
        }
        return books;
    }

    @Override
    public List<Bookshelf> getUnsoldBookshelves() {
        List<Bookshelf> books = bookStorageRepository.getUnsoldBookshelves();
        if (books.size() > 0) {
            Comparator<Bookshelf> bookComp = new BookshelfArrivalDateComparator().thenComparing(new BookshelfPriceComparator());
            books.sort(bookComp);
        }
        return books;
    }
}
