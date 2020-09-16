package repository.file;

import entity.Bookshelf;
import exeption.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import repository.base.StorageRepository;
import util.csv.StorageCsv;

import java.io.IOException;
import java.util.List;

public class FileStorageRepository implements StorageRepository {

    @Autowired
    private StorageCsv storageCsv;

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
    public Bookshelf update(Bookshelf bookshelf) throws RepositoryException {
        try {
            List<Bookshelf> bookshelves = storageCsv.readAllFromCsv();
            for (int i = 0; i < bookshelves.size(); i++) {
                if (bookshelves.get(i).getId() == bookshelf.getId()) {
                    bookshelves.set(i, bookshelf);
                    break;
                }
            }
            storageCsv.writeAllToCsv(bookshelves);
            return bookshelf;
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
