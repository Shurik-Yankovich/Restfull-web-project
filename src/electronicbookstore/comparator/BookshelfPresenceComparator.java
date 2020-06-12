package electronicbookstore.comparator;

import electronicbookstore.storage.Bookshelf;

import java.util.Comparator;

public class BookshelfPresenceComparator implements Comparator<Bookshelf> {
    @Override
    public int compare(Bookshelf bookshelf1, Bookshelf bookshelf2) {
        return Boolean.compare(bookshelf1.isPresence(), bookshelf2.isPresence());
    }
}
