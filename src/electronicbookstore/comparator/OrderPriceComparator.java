package electronicbookstore.comparator;

import electronicbookstore.store.BookOrder;

import java.util.Comparator;

public class OrderPriceComparator implements Comparator<BookOrder> {
    @Override
    public int compare(BookOrder order1, BookOrder order2) {
        return Double.compare(order1.getPrice(), order2.getPrice());
    }
}
