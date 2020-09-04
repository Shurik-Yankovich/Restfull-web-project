package bookstore.service.request;

import bookstore.entity.Book;
import bookstore.entity.Request;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.RequestRepository;
import bookstore.repository.file.FileRequestRepository;
import bookstore.util.comparator.RequestBookNameComparator;
import bookstore.util.comparator.RequestCountComparator;
import bookstore.util.serialize.ISerializationService;
import com.annotation.InjectByProperty;
import com.annotation.InjectByType;
import com.annotation.Singleton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static bookstore.entity.Status.*;

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
            return null;
        }
    }

//    private Request createNewRequest(Book book) throws RepositoryException {
//        Request request = new Request(book);
////        request.setId(requestRepository.readAll().size());
//        int count = changeCountByBook(book);
//        request.setCount(count);
//        return request;
//    }

    private int changeCountByBook(Book book) throws RepositoryException {
//        int count = getCountRequests(book);
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

//    private int getCountRequests(Book book) throws RepositoryException {
//        int result = 0;
//        for (Request request : requestRepository.readAll()) {
//            if (book.equals(request.getBook())) {
//                result++;
//            }
//        }
//        return result;
//    }

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
            return null;
        }
    }

    public Request completeRequest(Request request) {
        try {
            request.setStatus(COMPLETED);
            return requestRepository.update(request);
        } catch (RepositoryException e) {
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
            return null;
        }
    }

    @Override
    public List<Request> getRequestList() {
        try {
            return requestRepository.readAll();
        } catch (RepositoryException e) {
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
            return false;
        }
    }

    @Override
    public boolean writeAllToFile() {
        try {
            fileRequestRepository.createAll(requestRepository.readAll());
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public boolean writeRequestToFile(Request request) {
        try {
            fileRequestRepository.create(request);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public boolean updateRequestToFile(Request request) {
        try {
            fileRequestRepository.update(request);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public boolean save() {
        try {
            requestSerialize.save(requestRepository.readAll(), REQUEST_SERIALIZATION_FILE_NAME);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }
}
