package bookstore.util.comparator;

import bookstore.model.Bookshelf;

import java.util.Comparator;

public class BookshelfPublicationYearComparator implements Comparator<Bookshelf> {
    @Override
    public int compare(Bookshelf bookshelf1, Bookshelf bookshelf2) {
        return Integer.compare(bookshelf1.getBook().getPublicationYear(), bookshelf2.getBook().getPublicationYear());
    }
}
