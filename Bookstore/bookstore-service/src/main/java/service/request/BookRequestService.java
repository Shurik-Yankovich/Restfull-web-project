package service.request;

import entity.Book;
import entity.Request;
import exeption.RepositoryException;
//import logger.LoggerApp;
import repository.base.RequestRepository;
import repository.file.FileRequestRepository;
import service.order.BookOrderService;
import util.comparator.RequestBookNameComparator;
import util.comparator.RequestCountComparator;
import util.serialize.ISerializationService;
import com.annotation.InjectByProperty;
import com.annotation.InjectByType;
import com.annotation.Singleton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static entity.Status.*;

@Singleton
public class BookRequestService implements RequestService {

    @InjectByProperty(propertyName = "REQUEST_SERIALIZATION_FILE_NAME")
    private String REQUEST_SERIALIZATION_FILE_NAME;

    @InjectByType
    private RequestRepository requestRepository;
    @InjectByType
    private FileRequestRepository fileRequestRepository;
    @InjectByType
    private ISerializationService<Request> requestSerialize;

//    private LoggerApp logger;
//
//    public BookRequestService() {
//        logger = new LoggerApp(BookRequestService.class);
//    }

    /*@PostConstruct
    public void init() {
        try {
            requestRepository.createAll(requestSerialize.load(REQUEST_SERIALIZATION_FILE_NAME));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public Request addRequest(Book book) {
        try {
//            Request request = createNewRequest(book);
            Request request = new Request(book);
            request = requestRepository.create(request);
            request.setCount(changeCountByBook(book));
            return request;
        } catch (RepositoryException e) {
//            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    private int changeCountByBook(Book book) throws RepositoryException {
        int count = 1;
        boolean isIncreased = false;
        for (Request request : requestRepository.readAll()) {
            if (request.getBook().equals(book)) {
                if (!isIncreased) {
                    isIncreased = true;
                    count = request.getCount() + 1;
                }
                request.setCount(count);
                requestRepository.update(request);
            }
        }
        return count;
    }

    @Override
    public List<Integer> addRequestList(List<Book> books) {
        List<Integer> requestNumbers = new ArrayList<>();
        for (Book book : books) {
            requestNumbers.add(addRequest(book).getId());
        }
        return requestNumbers;
    }

    @Override
    public List<Request> completeRequestsByBook(Book book) {
        try {
            List<Request> requestList = new ArrayList<>();
            for (Request request : requestRepository.readAll()) {
                if (request.getBook().equals(book) && request.getStatus() == NEW) {
                    request.setStatus(COMPLETED);
                    requestRepository.update(request);
                    requestList.add(request);
                }
            }
            return requestList;
        } catch (RepositoryException e) {
//            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    public Request completeRequest(Request request) {
        try {
            request.setStatus(COMPLETED);
            return requestRepository.update(request);
        } catch (RepositoryException e) {
//            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    @Override
    public Request cancelRequest(Request request) throws RepositoryException {
        request.setStatus(CANCELED);
        return requestRepository.update(request);
    }

    @Override
    public boolean checkCompleteRequest(List<Integer> requestNumbers) {
        if (requestNumbers != null) {
            for (int number : requestNumbers) {
                if (getRequestByNumber(number).getStatus() != COMPLETED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Request getRequestByNumber(int requestNumber) {
        try {
            return requestRepository.read(requestNumber);
        } catch (RepositoryException e) {
//            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Request> getRequestList() {
        try {
            return requestRepository.readAll();
        } catch (RepositoryException e) {
//            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Request> getNewRequests() {
        try {
            List<Request> newRequests = new ArrayList<>();
            for (Request request : requestRepository.readAll()) {
                if (request.getStatus() == NEW) {
                    newRequests.add(request);
                }
            }
            return newRequests;
        } catch (RepositoryException e) {
//            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Request> getSortingRequestList() {
        try {
            List<Request> requests = new ArrayList<>(requestRepository.readAll());
            if (requests.size() > 0) {
                Comparator<Request> requestComp = new RequestCountComparator().thenComparing(new RequestBookNameComparator());
                requests.sort(requestComp);
            }
            return requests;
        } catch (RepositoryException e) {
//            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean readAllFromFile() {
        try {
            List<Request> requests = fileRequestRepository.readAll();
            requestRepository.createAll(requests);
            return true;
        } catch (RepositoryException e) {
//            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean writeAllToFile() {
        try {
            fileRequestRepository.createAll(requestRepository.readAll());
            return true;
        } catch (RepositoryException e) {
//            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean writeRequestToFile(Request request) {
        try {
            fileRequestRepository.create(request);
            return true;
        } catch (RepositoryException e) {
//            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateRequestToFile(Request request) {
        try {
            fileRequestRepository.update(request);
            return true;
        } catch (RepositoryException e) {
//            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean save() {
        try {
            requestSerialize.save(requestRepository.readAll(), REQUEST_SERIALIZATION_FILE_NAME);
            return true;
        } catch (RepositoryException e) {
//            logger.errorLogger(e.getMessage());
            return false;
        }
    }
}
