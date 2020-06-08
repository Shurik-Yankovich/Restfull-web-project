package electronicbookstore.comparator;

import electronicbookstore.store.arrays.Request;

import java.util.Comparator;

public class RequestStatusComparator implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        return request1.getStatus().compareTo(request2.getStatus());
    }
}
