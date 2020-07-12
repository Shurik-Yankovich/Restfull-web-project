package bookstore.service.storage;

import bookstore.entity.Bookshelf;
import bookstore.entity.Order;
import bookstore.entity.Request;
import bookstore.entity.book.Book;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.StorageRepository;
import bookstore.repository.file.FileStorageRepository;
import bookstore.service.request.RequestService;
import bookstore.util.comparator.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import static bookstore.entity.Status.COMPLETED;

public class BookStorageService implements StorageService {

    private final int NUMBER_OF_MONTHS_FOR_UNSOLD_BOOKS;
    private final boolean MARK_REQUESTS_AS_COMPLETED;

    private StorageRepository storageRepository;
    private RequestService requestService;
    private FileStorageRepository fileStorageRepository;


    public BookStorageService(StorageRepository storageRepository, RequestService requestService, Properties properties) {
        this.storageRepository = storageRepository;
        this.requestService = requestService;
        this.fileStorageRepository = new FileStorageRepository();
        NUMBER_OF_MONTHS_FOR_UNSOLD_BOOKS = Integer.parseInt(properties.getProperty("month_for_unsold_book"));
        MARK_REQUESTS_AS_COMPLETED = Boolean.parseBoolean(properties.getProperty("mark_requests_as_completed"));
    }

    @Override
    public Bookshelf addBookOnStorage(Book book, int count) throws RepositoryException {
        requestService.completeRequest(book);
        return storageRepository.update(book, count);
    }

    @Override
    public Bookshelf addBookOnStorage(Book book, int count, double price) throws RepositoryException {
        return storageRepository.create(new Bookshelf(book, count, price, LocalDate.now()));
    }

    @Override
    public double getTotalPrice(List<Book> books) throws RepositoryException {
        double price = 0;
        for (Book book : books) {
            price += storageRepository.read(book).getPrice();
        }
        return price;
    }

    @Override
    public List<Book> checkBooksNotInStorage(List<Book> books) throws RepositoryException {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (!bookReservation(book)) {
                result.add(book);
            }
        }
        return result;
    }

    private boolean bookReservation(Book book) throws RepositoryException {
        List<Bookshelf> bookshelves = storageRepository.readAll();
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
    public void cancelBookReservation(Order order) throws RepositoryException {
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

    private List<Request> getRequestFromOrder(Order order) throws RepositoryException {
        List<Request> requests = new ArrayList<>();
        for (Integer requestNumber : order.getNumbersRequest()) {
            requests.add(requestService.getRequestByNumber(requestNumber));
        }
        return requests;
    }

    private void changeBookCount(Book book) throws RepositoryException {
        List<Bookshelf> bookshelves = storageRepository.readAll();
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
    public List<Bookshelf> getBookshelfList() throws RepositoryException {
        return storageRepository.readAll();
    }

    @Override
    public List<Bookshelf> getSortingBookshelves() throws RepositoryException {
        List<Bookshelf> books = new ArrayList<>(storageRepository.readAll());
        if (books.size() > 0) {
            Comparator<Bookshelf> bookComp = new BookshelfTitleComparator().thenComparing(new BookshelfPublicationYearComparator())
                    .thenComparing(new BookshelfPriceComparator()).thenComparing(new BookshelfPresenceComparator());
            books.sort(bookComp);
        }
        return books;
    }

    @Override
    public List<Bookshelf> getUnsoldBookshelves() throws RepositoryException {
        List<Bookshelf> bookshelves = getBooksBeforeArrivalDate();
        if (bookshelves.size() > 0) {
            Comparator<Bookshelf> bookComp = new BookshelfArrivalDateComparator().thenComparing(new BookshelfPriceComparator());
            bookshelves.sort(bookComp);
        }
        return bookshelves;
    }

    private List<Bookshelf> getBooksBeforeArrivalDate() throws RepositoryException {
        LocalDate arrivalDate = LocalDate.now().minusMonths(NUMBER_OF_MONTHS_FOR_UNSOLD_BOOKS);
        List<Bookshelf> booksBeforeArrivalDate = new ArrayList<>();
        for (Bookshelf bookshelf : storageRepository.readAll()) {
            if (bookshelf.getArrivalDate().isBefore(arrivalDate) && bookshelf.getCount() > 0) {
                booksBeforeArrivalDate.add(bookshelf);
            }
        }
        return booksBeforeArrivalDate;
    }

    @Override
    public void readAllFromFile() throws RepositoryException {
        List<Bookshelf> bookshelves = fileStorageRepository.readAll();
        storageRepository.createAll(bookshelves);
    }

    @Override
    public void writeAllToFile() throws RepositoryException {
        fileStorageRepository.createAll(storageRepository.readAll());
    }

    @Override
    public void writeBookshelfToFile(Bookshelf bookshelf) throws RepositoryException {
        fileStorageRepository.create(bookshelf);
    }

    @Override
    public void updateBookshelfToFile(Bookshelf bookshelf) throws RepositoryException {
        fileStorageRepository.update(bookshelf, null);
    }
}
