package bookstore.repository.file;

import bookstore.entity.Bookshelf;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.Repository;
import bookstore.util.csv.StorageCsv;
import com.annotation.InjectByType;

import java.io.IOException;
import java.util.List;

public class FileStorageRepository implements Repository<Bookshelf, Integer, Integer, Bookshelf> {

    @InjectByType
    private StorageCsv storageCsv;

//    public FileStorageRepository() {
//        storageCsv = new StorageCsv();
//    }

//    public FileStorageRepository(StorageCsv storageCsv) {
//        this.storageCsv = storageCsv;
//    }

    @Override
    public Bookshelf create(Bookshelf bookshelf) throws RepositoryException {
        try {
            List<Bookshelf> bookshelves = storageCsv.readAllFromCsv();
            for (Bookshelf book : bookshelves) {
                if (book.getId() == bookshelf.getId()) {
                    bookshelf.setId(bookshelves.size());
                }
            }
            storageCsv.writeToCsv(bookshelf);
            return bookshelf;
        } catch (IOException e) {
            throw new RepositoryException("Ошибка записи данных о книге в файл!");
        }
    }

    @Override
    public Bookshelf update(Bookshelf book, Integer integer) throws RepositoryException {
        try {
            List<Bookshelf> bookshelves = storageCsv.readAllFromCsv();
            for (int i = 0; i < bookshelves.size(); i++) {
                if (bookshelves.get(i).getId() == book.getId()) {
                    bookshelves.set(i, book);
                    break;
                }
            }
            storageCsv.writeAllToCsv(bookshelves);
            return book;
        } catch (IOException e) {
            throw new RepositoryException("Ошибка обновления данных о книге в файле!");
        }
    }

    @Override
    public Bookshelf read(Integer id) throws RepositoryException {
        try {
            return storageCsv.readFromCsv(id);
        } catch (IOException e) {
            throw new RepositoryException("Ошибка чтения данных о книге из файла!");
        }
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Bookshelf> readAll() throws RepositoryException {
        try {
            return storageCsv.readAllFromCsv();
        } catch (IOException e) {
            throw new RepositoryException("Ошибка чтения данных о книгах из файла!");
        }
    }

    @Override
    public void createAll(List<Bookshelf> bookshelves) throws RepositoryException {
        try {
            storageCsv.writeAllToCsv(bookshelves);
        } catch (IOException e) {
            throw new RepositoryException("Ошибка записи данных о книгах в файл!");
        }
    }
}
