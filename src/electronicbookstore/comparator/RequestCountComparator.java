package electronicbookstore.comparator;

import electronicbookstore.store.arrays.Request;

import java.util.Comparator;

public class RequestCountComparator implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        return Integer.compare(request1.getCount(), request2.getCount());
    }
}
