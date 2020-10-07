package service.request;

import entity.Book;
import entity.Request;
import exeption.RepositoryException;
import logger.LoggerApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.base.RequestRepository;
import repository.file.FileRequestRepository;
import util.comparator.RequestBookNameComparator;
import util.comparator.RequestCountComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static entity.Status.*;

@Service
public class BookRequestService implements RequestService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private FileRequestRepository fileRequestRepository;

    private final LoggerApp logger = new LoggerApp(this.getClass());

    @Override
    public Request addRequest(Book book) {
        try {
            Request request = new Request(book);
            request.setCount(changeCountByBook(book));
            request = requestRepository.create(request);
            return request;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
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
    public List<Request> addRequestList(List<Book> books) {
        List<Request> requestNumbers = new ArrayList<>();
        for (Book book : books) {
            requestNumbers.add(addRequest(book));
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
            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    public Request completeRequest(Request request) {
        try {
            request.setStatus(COMPLETED);
            return requestRepository.update(request);
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
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
            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Request> getRequestList() {
        try {
            return requestRepository.readAll();
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
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
            logger.errorLogger(e.getMessage());
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
            logger.errorLogger(e.getMessage());
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
            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean writeAllToFile() {
        try {
            fileRequestRepository.createAll(requestRepository.readAll());
            return true;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean writeRequestToFile(Request request) {
        try {
            fileRequestRepository.create(request);
            return true;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateRequestToFile(Request request) {
        try {
            fileRequestRepository.update(request);
            return true;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return false;
        }
    }
}
