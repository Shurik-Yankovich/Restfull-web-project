package bookstore.service.storage;

import bookstore.model.book.Book;
import bookstore.model.Bookshelf;
import bookstore.repository.storage.ListStorageRepository;
import bookstore.repository.storage.StorageRepository;
import bookstore.util.BookGenerator;
import bookstore.util.comparator.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static bookstore.service.request.BookRequestService.REQUEST_SERVICE;

public class BookStorageService implements StorageService {

    public static final StorageService STORAGE_SERVICE = new BookStorageService(new ListStorageRepository(BookGenerator.generate()));

    private StorageRepository bookStorageRepository;

    private BookStorageService(StorageRepository bookStorageRepository) {
        this.bookStorageRepository = bookStorageRepository;
    }

    @Override
    public void addBookOnStorage(Book book, int count) {
        REQUEST_SERVICE.completeRequest(book);
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
    public List<Bookshelf> getBookshelfList() {
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
