package electronicbookstore.comparator;

import electronicbookstore.storage.Bookshelf;

import java.util.Comparator;

public class BookshelfPriceComparator implements Comparator<Bookshelf> {
    @Override
    public int compare(Bookshelf bookshelf1, Bookshelf bookshelf2) {
        return Double.compare(bookshelf1.getPrice(), bookshelf2.getPrice());
    }
}