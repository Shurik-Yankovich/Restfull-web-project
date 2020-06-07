package electronicbookstore.comparator;

import electronicbookstore.store.BookOrder;

import java.util.Comparator;

public class OrderCompletionDateComparator implements Comparator<BookOrder> {
    @Override
    public int compare(BookOrder order1, BookOrder order2) {
        return order1.getOrderCompletionDate().compareTo(order2.getOrderCompletionDate());
    }
}
