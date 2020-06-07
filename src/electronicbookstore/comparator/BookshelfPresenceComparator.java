package electronicbookstore.comparator;

import electronicbookstore.storage.Bookshelf;

import java.util.Comparator;

public class BookshelfPresenceComparator implements Comparator<Bookshelf> {
    @Override
    public int compare(Bookshelf bookshelf1, Bookshelf bookshelf2) {
        if (bookshelf1.isPresence() && !bookshelf2.isPresence())
            return 1;
        else if (!bookshelf1.isPresence() && bookshelf2.isPresence())
            return -1;
        else
            return 0;
    }
}
