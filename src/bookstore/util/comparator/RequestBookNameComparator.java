package bookstore.util.comparator;

import bookstore.entity.Request;

import java.util.Comparator;

public class RequestBookNameComparator implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        return request1.getBook().getTitle().compareTo(request2.getBook().getTitle());
    }
}
