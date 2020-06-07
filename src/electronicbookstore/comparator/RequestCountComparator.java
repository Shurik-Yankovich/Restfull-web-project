package electronicbookstore.comparator;

import electronicbookstore.store.arrays.BookRequest;

import java.util.Comparator;

public class RequestCountComparator implements Comparator<BookRequest> {
    @Override
    public int compare(BookRequest request1, BookRequest request2) {
        return Integer.compare(request1.getCount(), request2.getCount());
    }
}
