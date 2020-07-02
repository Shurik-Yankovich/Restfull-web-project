package bookstore.repository.request;

import bookstore.model.Request;
import bookstore.model.Status;

import java.util.ArrayList;
import java.util.List;

public class StoreRequestRepository implements RequestRepository {

    private List<Request> array;

    public StoreRequestRepository() {
        this.array = new ArrayList<>();
    }

    @Override
    public String toString() {
        return array.toString();
    }

    @Override
    public Request create(Request request) {
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
    public void createAll(List<Request> list) {
        array.addAll(list);
    }

    private Request searchByRequestNumber(int requestNumber) {
        for (Request request : array) {
            if (request.getIndex() == requestNumber) {
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
