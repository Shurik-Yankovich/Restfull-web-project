package electronicbookstore.comparator;

import electronicbookstore.storage.Bookshelf;

import java.util.Comparator;

public class BookshelfArrivalDateComparator implements Comparator<Bookshelf> {
    @Override
    public int compare(Bookshelf bookshelf1, Bookshelf bookshelf2) {
        return bookshelf1.getArrivalDate().compareTo(bookshelf2.getArrivalDate());
    }
}