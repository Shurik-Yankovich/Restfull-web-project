package electronicbookstore.comparator;

import electronicbookstore.store.BookRequest;

import java.util.Comparator;

public class RequestStatusComparator implements Comparator<BookRequest> {
    @Override
    public int compare(BookRequest request1, BookRequest request2) {
        return request1.getStatus().compareTo(request2.getStatus());
    }
}
