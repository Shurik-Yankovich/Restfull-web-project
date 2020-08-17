package bookstore.repository.list;

import bookstore.entity.Request;
import bookstore.entity.Status;
import bookstore.repository.base.RequestRepository;

import java.util.ArrayList;
import java.util.List;

public class BookRequestRepository implements RequestRepository {

    private List<Request> array;

    public BookRequestRepository() {
        array = new ArrayList<>();
    }

    public BookRequestRepository(List<Request> array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return array.toString();
    }

    @Override
    public Request create(Request request) {
        int index = array.size();
        request.setId(index);
        array.add(request);
        return request;
    }

    @Override
    public Request update(Integer numberRequest, Status status) {
        Request request = read(numberRequest);
        if (request != null) {
            request.setStatus(status);
        }
        return request;
    }

    @Override
    public Request read(Integer requestNumber) {
        Request request = null;
        if (requestNumber >= 0 && requestNumber < array.size()) {
            request = searchByRequestNumber(requestNumber);
        }
        return request;
    }

    @Override
    public void delete(Integer requestNumber) {
        if (requestNumber >= 0 && requestNumber < array.size()) {
            Request request = searchByRequestNumber(requestNumber);
            array.remove(request);
        }
    }

    @Override
    public void createAll(List<Request> requests) {
        array = requests;
    }

    private Request searchByRequestNumber(int requestNumber) {
        for (Request request : array) {
            if (request.getId() == requestNumber) {
                return request;
            }
        }
        return null;
    }

    @Override
    public List<Request> readAll() {
        return array;
    }
}
