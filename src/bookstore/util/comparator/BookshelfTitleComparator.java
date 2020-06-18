package bookstore.util.comparator;

import bookstore.model.Bookshelf;

import java.util.Comparator;

public class BookshelfTitleComparator implements Comparator<Bookshelf> {
    @Override
    public int compare(Bookshelf bookshelf1, Bookshelf bookshelf2) {
        return bookshelf1.getBook().getTitle().compareTo(bookshelf2.getBook().getTitle());
    }
}
