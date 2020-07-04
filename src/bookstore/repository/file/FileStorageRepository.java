package bookstore.repository.file;

import bookstore.entity.Bookshelf;
import bookstore.repository.base.Repository;
import bookstore.util.csv.StorageCsv;

import java.util.List;

public class FileStorageRepository implements Repository<Bookshelf, Integer, Integer, Bookshelf> {

    private StorageCsv storageCsv;

    public FileStorageRepository() {
        storageCsv = new StorageCsv();
    }

    @Override
    public Bookshelf create(Bookshelf bookshelf) {
        storageCsv.writeToCsv(bookshelf);
        return bookshelf;
    }

    @Override
    public Bookshelf update(Bookshelf book, Integer integer) {
        List<Bookshelf> bookshelves = storageCsv.readAllFromCsv();
        for (Bookshelf bookshelf : bookshelves) {
            if (bookshelf.getId() == book.getId()) {
                bookshelf = book;
                break;
            }
        }
        storageCsv.writeAllToCsv(bookshelves);
        return book;
    }

    @Override
    public Bookshelf read(Integer id) {
        return storageCsv.readFromCsv(id);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Bookshelf> readAll() {
        return storageCsv.readAllFromCsv();
    }

    @Override
    public void createAll(List<Bookshelf> bookshelves) {
        storageCsv.writeAllToCsv(bookshelves);
    }
}
