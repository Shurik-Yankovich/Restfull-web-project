package electronicbookstore.comparator;

import electronicbookstore.store.BookRequest;

import java.util.Comparator;

public class RequestBookNameComparator implements Comparator<BookRequest> {
    @Override
    public int compare(BookRequest request1, BookRequest request2) {
        return request1.getBook().toString().compareTo(request2.getBook().toString());
    }
}
