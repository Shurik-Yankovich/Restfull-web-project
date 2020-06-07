package electronicbookstore.comparator;

import electronicbookstore.store.BookOrder;

import java.util.Comparator;

public class OrderStatusComparator implements Comparator<BookOrder> {
    @Override
    public int compare(BookOrder order1, BookOrder order2) {
        return order1.getStatus().compareTo(order2.getStatus());
    }
}
