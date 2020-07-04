package bookstore.repository.file;

import bookstore.entity.Bookshelf;
import bookstore.repository.base.Repository;
import bookstore.util.csv.BookshelfCsv;

import java.util.List;

public class FileStorageRepository implements Repository<Bookshelf, Integer, Integer, Bookshelf> {

    private BookshelfCsv bookshelfCsv;

    public FileStorageRepository() {
        bookshelfCsv = new BookshelfCsv();
    }

    @Override
    public Bookshelf create(Bookshelf bookshelf) {
        bookshelfCsv.writeToCsv(bookshelf);
        return bookshelf;
    }

    @Override
    public Bookshelf update(Bookshelf book, Integer integer) {
        List<Bookshelf> bookshelves = bookshelfCsv.readAllFromCsv();
        for (Bookshelf bookshelf : bookshelves) {
            if (bookshelf.getId() == book.getId()) {
                bookshelf = book;
                break;
            }
        }
        bookshelfCsv.writeAllToCsv(bookshelves);
        return book;
    }

    @Override
    public Bookshelf read(Integer id) {
        return bookshelfCsv.readFromCsv(id);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Bookshelf> readAll() {
        return bookshelfCsv.readAllFromCsv();
    }

    @Override
    public void createAll(List<Bookshelf> bookshelves) {
        bookshelfCsv.writeAllToCsv(bookshelves);
    }
}
