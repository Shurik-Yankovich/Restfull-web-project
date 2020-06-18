package electronicbookstore.repository.request;

import electronicbookstore.model.Book;
import electronicbookstore.model.Request;
import electronicbookstore.model.Status;

public interface RequestRepository {

    int add(Request element);
    void closeRequest(Book book);
    void changeStatus(int numberRequest, Status status);
    Request getByRequestNumber(int requestNumber);
    Request get(int index);
    Request[] getArray();
    int size();
}
