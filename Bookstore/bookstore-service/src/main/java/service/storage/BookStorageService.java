package service.storage;

import entity.Bookshelf;
import entity.Order;
import entity.Request;
import entity.Book;
import exeption.RepositoryException;
import logger.LoggerApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import repository.base.StorageRepository;
import repository.file.FileStorageRepository;
import service.request.RequestService;
import util.comparator.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static entity.Status.COMPLETED;

@Service
public class BookStorageService implements StorageService {

    @Value("${month_for_unsold_book:6}")
    private Integer NUMBER_OF_MONTHS_FOR_UNSOLD_BOOKS;
    @Value("${mark_requests_as_completed:true}")
    private Boolean MARK_REQUESTS_AS_COMPLETED;

    @Autowired
    private RequestService requestService;
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private FileStorageRepository fileStorageRepository;

    private final LoggerApp logger = new LoggerApp(this.getClass());

    @Override
    public Bookshelf addBookOnStorage(Book book, int count) {
        try {
            if (MARK_REQUESTS_AS_COMPLETED) {
                requestService.completeRequestsByBook(book);
            }
            Bookshelf bookshelf = searchBook(book);
            if(bookshelf!=null) {
                int currentCount = bookshelf.getCount();
                bookshelf.setCount(currentCount + count);
                return storageRepository.update(bookshelf);
            }
            logger.warningLogger("Данной книги нет в списках!");
            return null;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    private Bookshelf searchBook(Book book) throws RepositoryException {
//        for (Bookshelf bookshelf : storageRepository.readAll()) {
//            if (bookshelf.getBook().equals(book)) {
//                return bookshelf;
//            }
//        }
//        return null;
        return storageRepository.read(book.getId());
    }

    @Override
    public Bookshelf addBookOnStorage(Book book, int count, double price) {
        try {
            return storageRepository.create(new Bookshelf(book, count, price, LocalDate.now()));
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    @Override
    public double getTotalPrice(List<Book> books) throws RepositoryException {
        double price = 0;
        for (Book book : books) {
//            price += storageRepository.read(searchBook(book).getId()).getPrice();
            price += storageRepository.read(book.getId()).getPrice();
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
            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    private boolean bookReservation(Book book) throws RepositoryException {
        Bookshelf bookshelf = searchBook(book);
        if (bookshelf != null) {
            int count = bookshelf.getCount();
            if (count > 0) {
                bookshelf.setCount(count - 1);
                storageRepository.update(bookshelf);
                return true;
            }
        }
        return false;
    }

    @Override
    public void cancelBookReservation(Order order) throws RepositoryException {
//        List<Request> requestList = getRequestFromOrder(order);
        List<Request> requestList = order.getRequests();
        Request request;
        if (requestList != null) {
            for (Book book : order.getBooks()) {
                request = searchBookInRequest(book, requestList);
                if (request == null || request.getStatus() == COMPLETED) {
                    changeBookCount(book);
                }
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

    /*private List<Request> getRequestFromOrder(Order order) {
        List<Request> requests = new ArrayList<>();
        for (Integer requestNumber : order.getNumbersRequest()) {
            requests.add(requestService.getRequestByNumber(requestNumber));
        }
        return requests;
    }*/

    private void changeBookCount(Book book) throws RepositoryException {
        Bookshelf bookshelf = searchBook(book);
        int count = bookshelf.getCount();
        bookshelf.setCount(count + 1);
        storageRepository.update(bookshelf);
    }

    @Override
    public Bookshelf getBookshelf(Book book) {
        try {
            return searchBook(book);
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Bookshelf> getBookshelfList() {
        try {
            return storageRepository.readAll();
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
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
            logger.errorLogger(e.getMessage());
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
            logger.errorLogger(e.getMessage());
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
            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean writeAllToFile() {
        try {
            fileStorageRepository.createAll(storageRepository.readAll());
            return true;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean writeBookshelfToFile(Bookshelf bookshelf) {
        try {
            fileStorageRepository.create(bookshelf);
            return true;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateBookshelfToFile(Bookshelf bookshelf) {
        try {
            fileStorageRepository.update(bookshelf);
            return true;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return false;
        }
    }
}
