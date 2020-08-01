package bookstore.service.request;

import bookstore.entity.Request;
import bookstore.entity.book.Book;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.RequestRepository;
import bookstore.repository.file.FileRequestRepository;
import bookstore.util.comparator.RequestBookNameComparator;
import bookstore.util.comparator.RequestCountComparator;
import bookstore.util.serialize.ISerializationService;
import bookstore.util.serialize.SerializationService;
import com.annotation.InjectByType;
import com.annotation.Singleton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static bookstore.constant.FileName.REQUEST_SERIALIZATION_FILE_NAME;
import static bookstore.entity.Status.*;

@Singleton
public class BookRequestService implements RequestService {

    @InjectByType
    private RequestRepository requestRepository;
    @InjectByType
    private FileRequestRepository fileRequestRepository;

//    public BookRequestService(RequestRepository requestRepository, FileRequestRepository fileRequestRepository) {
//        this.requestRepository = requestRepository;
//        this.fileRequestRepository = fileRequestRepository;
//    }

    @Override
    public Request addRequest(Book book) {
        try {
            Request request = createNewRequest(book);
            return requestRepository.create(request);
        } catch (RepositoryException e) {
            return null;
        }
    }

    private Request createNewRequest(Book book) throws RepositoryException {
        Request request = new Request(book);
//        request.setId(requestRepository.readAll().size());
        int count = changeCountByBook(book);
        request.setCount(count);
        return request;
    }

    private int changeCountByBook(Book book) throws RepositoryException {
        int count = getCountRequests(book);
        for (Request request : requestRepository.readAll()) {
            if (request.getBook().equals(book)) {
                request.setCount(count);
            }
        }
        return count;
    }

    private int getCountRequests(Book book) throws RepositoryException {
        int result = 0;
        for (Request request : requestRepository.readAll()) {
            if (book.equals(request.getBook())) {
                result++;
            }
        }
        return result;
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
                    requestList.add(request);
                }
            }
            return requestList;
        } catch (RepositoryException e) {
            return null;
        }
    }

    public Request completeRequest(Request request) {
        request.setStatus(COMPLETED);
        return request;
    }

    @Override
    public Request cancelRequest(int number) throws RepositoryException {
        return requestRepository.update(number, CANCELED);
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
            fileRequestRepository.update(request, null);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public boolean save() {
        try {
            ISerializationService<Request> requestSerialize = new SerializationService<>();
            requestSerialize.save(requestRepository.readAll(), REQUEST_SERIALIZATION_FILE_NAME);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }
}
