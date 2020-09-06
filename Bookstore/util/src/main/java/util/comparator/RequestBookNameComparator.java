package util.comparator;

import entity.Request;

import java.util.Comparator;

public class RequestBookNameComparator implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        return request1.getBook().getTitle().compareTo(request2.getBook().getTitle());
    }
}
