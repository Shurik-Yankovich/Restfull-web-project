package electronicbookstore.comparator;

import electronicbookstore.store.arrays.Request;

import java.util.Comparator;

public class RequestBookNameComparator implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        return request1.getBook().compareTo(request2.getBook());
    }
}
