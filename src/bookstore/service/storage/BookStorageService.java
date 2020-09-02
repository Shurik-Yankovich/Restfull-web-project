package bookstore.service.storage;

import bookstore.entity.Bookshelf;
import bookstore.entity.Order;
import bookstore.entity.Request;
import bookstore.entity.Book;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.StorageRepository;
import bookstore.repository.file.FileStorageRepository;
import bookstore.service.request.RequestService;
import bookstore.util.comparator.*;
import bookstore.util.serialize.ISerializationService;
import com.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static bookstore.entity.Status.COMPLETED;

@Singleton
public class BookStorageService implements StorageService {

    @InjectByProperty(configName = "config.properties", propertyName = "month_for_unsold_book", type = Types.INTEGER)
    private int NUMBER_OF_MONTHS_FOR_UNSOLD_BOOKS;
    @InjectByProperty (propertyName = "mark_requests_as_completed")
    private boolean MARK_REQUESTS_AS_COMPLETED;
    @InjectByProperty(propertyName = "STORAGE_SERIALIZATION_FILE_NAME")
    private String STORAGE_SERIALIZATION_FILE_NAME;

    @InjectByType
    private RequestService requestService;
    @InjectByType
    private StorageRepository storageRepository;
    @InjectByType
    private FileStorageRepository fileStorageRepository;
    @InjectByType
    private ISerializationService<Bookshelf> storageSerialize;


    @PostConstruct
    public void init() {
        try {
            storageRepository.createAll(storageSerialize.load(STORAGE_SERIALIZATION_FILE_NAME));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bookshelf addBookOnStorage(Book book, int count) {
        try {
            if (MARK_REQUESTS_AS_COMPLETED) {
                requestService.completeRequestsByBook(book);
            }
            Bookshelf bookshelf = searchBook(book);
            int currentCount = bookshelf.getCount();
            bookshelf.setCount(currentCount + count);
            return storageRepository.update(bookshelf);
        } catch (RepositoryException e) {
            return null;
        }
    }

    private Bookshelf searchBook(Book book) throws RepositoryException {
        Bookshelf bookshelf;
        for (int i = 0; i < getBookshelfList().size(); i++) {
            bookshelf = storageRepository.read(i);
            if (bookshelf.getBook().equals(book)) {
                return bookshelf;
            }
        }
        return null;
    }

    @Override
    public Bookshelf addBookOnStorage(Book book, int count, double price) {
        try {
            return storageRepository.create(new Bookshelf(book, count, price, LocalDate.now()));
        } catch (RepositoryException e) {
            return null;
        }
    }

    @Override
    public double getTotalPrice(List<Book> books) throws RepositoryException {
        double price = 0;
        for (Book book : books) {
            //кастыль
            price += storageRepository.read(searchBook(book).getId()).getPrice();
        }
        return price;
    }

    @Override
    public List<Book> checkBooksNotInStorage(List<Book> books) {
        try {
            List<Book> result = new ArrayList<>();
            for (Book book : books) {
                if (!bookReservation(book)) {
                    result.add(book);
                }
            }
            return result;
        } catch (RepositoryException e) {
            return null;
        }
    }

    private boolean bookReservation(Book book) throws RepositoryException {
        List<Bookshelf> bookshelves = storageRepository.readAll();
        Bookshelf bookshelf = searchBook(book, bookshelves);
        if (bookshelf != null) {
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
        Bookshelf bookshelf = getBookshelf(book);
        int count = bookshelf.getCount();
        bookshelf.setCount(count + 1);
    }

    private Bookshelf searchBook(Book book, List<Bookshelf> bookshelves) {
        for (Bookshelf bookshelf : bookshelves) {
            if (bookshelf.getBook().equals(book)) {
                return bookshelf;
            }
        }
        return null;
    }

    @Override
    public Bookshelf getBookshelf(Book book) {
        try {
            List<Bookshelf> bookshelves = storageRepository.readAll();
            return searchBook(book, bookshelves);
        } catch (RepositoryException e) {
            return null;
        }
    }

    @Override
    public List<Bookshelf> getBookshelfList() {
        try {
            return storageRepository.readAll();
        } catch (RepositoryException e) {
            return null;
        }
    }

    @Override
    public List<Bookshelf> getSortingBookshelves() {
        try {
            List<Bookshelf> books = new ArrayList<>(storageRepository.readAll());
            if (books.size() > 0) {
                Comparator<Bookshelf> bookComp = new BookshelfTitleComparator().thenComparing(new BookshelfPublicationYearComparator())
                        .thenComparing(new BookshelfPriceComparator()).thenComparing(new BookshelfPresenceComparator());
                books.sort(bookComp);
            }
            return books;
        } catch (RepositoryException e) {
            return null;
        }
    }

    @Override
    public List<Bookshelf> getUnsoldBookshelves() {
        try {
            List<Bookshelf> bookshelves = getBooksBeforeArrivalDate();
            if (bookshelves.size() > 0) {
                Comparator<Bookshelf> bookComp = new BookshelfArrivalDateComparator().thenComparing(new BookshelfPriceComparator());
                bookshelves.sort(bookComp);
            }
            return bookshelves;
        } catch (RepositoryException e) {
            return null;
        }
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
    public boolean readAllFromFile() {
        try {
            List<Bookshelf> bookshelves = fileStorageRepository.readAll();
            storageRepository.createAll(bookshelves);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public boolean writeAllToFile() {
        try {
            fileStorageRepository.createAll(storageRepository.readAll());
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public boolean writeBookshelfToFile(Bookshelf bookshelf) {
        try {
            fileStorageRepository.create(bookshelf);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public boolean updateBookshelfToFile(Bookshelf bookshelf) {
        try {
            fileStorageRepository.update(bookshelf);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public boolean save() {
        try {
            storageSerialize.save(storageRepository.readAll(), STORAGE_SERIALIZATION_FILE_NAME);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }
}
