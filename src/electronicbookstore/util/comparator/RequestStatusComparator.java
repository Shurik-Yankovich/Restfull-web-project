package electronicbookstore.util.comparator;

import electronicbookstore.model.Request;

import java.util.Comparator;

public class RequestStatusComparator implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        return request1.getStatus().compareTo(request2.getStatus());
    }
}
