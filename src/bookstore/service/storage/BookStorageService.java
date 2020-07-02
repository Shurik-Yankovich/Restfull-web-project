package bookstore.service.storage;

import bookstore.model.Bookshelf;
import bookstore.model.Order;
import bookstore.model.Request;
import bookstore.model.book.Book;
import bookstore.repository.storage.StorageRepository;
import bookstore.service.request.RequestService;
import bookstore.util.comparator.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static bookstore.model.Status.COMPLETED;

public class BookStorageService implements StorageService {

    private static final int NUMBER_OF_MONTHS_FOR_UNSOLD_BOOKS = 6;

    private StorageRepository bookStorageRepository;
    private RequestService requestService;

    public BookStorageService(StorageRepository bookStorageRepository, RequestService requestService) {
        this.bookStorageRepository = bookStorageRepository;
        this.requestService = requestService;
    }

    @Override
    public void addBookOnStorage(Book book, int count) {
        requestService.completeRequest(book);
        bookStorageRepository.update(book, count);
    }

    @Override
    public double getTotalPrice(List<Book> books) {
        double price = 0;
        for (Book book : books) {
            price += bookStorageRepository.read(book).getPrice();
        }
        return price;
    }

    @Override
    public List<Book> checkBooksNotInStorage(List<Book> books) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (!bookReservation(book)) {
                result.add(book);
            }
        }
        return result;
    }

    private boolean bookReservation(Book book) {
        List<Bookshelf> bookshelves = bookStorageRepository.readAll();
        int index = searchBook(book, bookshelves);
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

    @Override
    public void cancelBookReservation(Order order) {
        List<Request> requestList = getRequestFromOrder(order);
        Request request;
        for (Book book : order.getBooks()) {
            request = searchBookInRequest(book, requestList);
            if (request == null || request.getStatus() == COMPLETED) {
                changeBookCount(book);
            }
        }
    }

    private Request searchBookInRequest(Book book, List<Request> requestList) {
        for (Request request : requestList) {
            if (request.getBook().equals(book)) {
                return request;
            }
        }
        return null;
    }

    private List<Request> getRequestFromOrder(Order order) {
        List<Request> requests = new ArrayList<>();
        for (Integer requestNumber : order.getNumbersRequest()) {
            requests.add(requestService.getRequestByNumber(requestNumber));
        }
        return requests;
    }

    private void changeBookCount(Book book) {
        List<Bookshelf> bookshelves = bookStorageRepository.readAll();
        int index = searchBook(book, bookshelves);
        Bookshelf bookshelf = bookshelves.get(index);
        int count = bookshelf.getCount();
        bookshelf.setCount(count + 1);
    }

    private int searchBook(Book book, List<Bookshelf> bookshelves) {
        for (int i = 0; i < bookshelves.size(); i++) {
            if (bookshelves.get(i).getBook().equals(book)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public List<Bookshelf> getBookshelfList() {
        return bookStorageRepository.readAll();
    }

    @Override
    public List<Bookshelf> getSortingBookshelves() {
        List<Bookshelf> books = new ArrayList<>(bookStorageRepository.readAll());
        if (books.size() > 0) {
            Comparator<Bookshelf> bookComp = new BookshelfTitleComparator().thenComparing(new BookshelfPublicationYearComparator())
                    .thenComparing(new BookshelfPriceComparator()).thenComparing(new BookshelfPresenceComparator());
            books.sort(bookComp);
        }
        return books;
    }

    @Override
    public List<Bookshelf> getUnsoldBookshelves() {
        List<Bookshelf> bookshelves = getBooksBeforeArrivalDate();
        if (bookshelves.size() > 0) {
            Comparator<Bookshelf> bookComp = new BookshelfArrivalDateComparator().thenComparing(new BookshelfPriceComparator());
            bookshelves.sort(bookComp);
        }
        return bookshelves;
    }

    private List<Bookshelf> getBooksBeforeArrivalDate() {
        LocalDate arrivalDate = LocalDate.now().minusMonths(NUMBER_OF_MONTHS_FOR_UNSOLD_BOOKS);
        List<Bookshelf> booksBeforeArrivalDate = new ArrayList<>();
        for (Bookshelf bookshelf : bookStorageRepository.readAll()) {
            if (bookshelf.getArrivalDate().isBefore(arrivalDate) && bookshelf.getCount() > 0) {
                booksBeforeArrivalDate.add(bookshelf);
            }
        }
        return booksBeforeArrivalDate;
    }
}
