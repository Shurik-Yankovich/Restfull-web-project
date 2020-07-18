package bookstore.util.comparator;

import bookstore.entity.Request;

import java.util.Comparator;

public class RequestStatusComparator implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        return request1.getStatus().compareTo(request2.getStatus());
    }
}
