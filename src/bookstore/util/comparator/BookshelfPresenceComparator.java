package bookstore.util.comparator;

import bookstore.model.Bookshelf;

import java.util.Comparator;

public class BookshelfPresenceComparator implements Comparator<Bookshelf> {
    @Override
    public int compare(Bookshelf bookshelf1, Bookshelf bookshelf2) {
        return Integer.compare(bookshelf1.getCount(), bookshelf2.getCount());
    }
}
