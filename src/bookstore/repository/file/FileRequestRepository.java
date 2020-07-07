package bookstore.repository.file;

import bookstore.entity.Request;
import bookstore.repository.base.Repository;
import bookstore.util.csv.RequestCsv;

import java.util.List;

public class FileRequestRepository implements Repository<Request, Integer, Integer, Request> {

    private RequestCsv requestCsv;

    public FileRequestRepository() {
        requestCsv = new RequestCsv();
    }

    @Override
    public Request create(Request request) {
        List<Request> requestList = requestCsv.readAllFromCsv();
        for (Request bookRequest : requestList) {
            if (bookRequest.getId() == request.getId()) {
                request.setId(requestList.size());
            }
        }
        requestCsv.writeToCsv(request);
        return request;
    }

    @Override
    public Request update(Request request, Integer integer) {
        List<Request> requestList = requestCsv.readAllFromCsv();
        boolean isPresent = false;
        for (int i = 0; i < requestList.size(); i++) {
            if (requestList.get(i).getId() == request.getId()) {
                requestList.set(i, request);
                isPresent = true;
                break;
            }
        }
        if (!isPresent) {
            requestList.add(request);
        }
        requestCsv.writeAllToCsv(requestList);
        return request;
    }

    @Override
    public Request read(Integer id) {
        return requestCsv.readFromCsv(id);
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public List<Request> readAll() {
        return requestCsv.readAllFromCsv();
    }

    @Override
    public void createAll(List<Request> requestList) {
        requestCsv.writeAllToCsv(requestList);
    }
}
