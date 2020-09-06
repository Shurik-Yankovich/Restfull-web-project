package repository.list;

import entity.Bookshelf;
import entity.Book;
import repository.base.StorageRepository;

import java.util.List;

public class BookStorageRepository implements StorageRepository {

    private List<Bookshelf> array;

    @Override
    public Bookshelf create(Bookshelf bookshelf) {
        int index = array.size();
        bookshelf.setId(index);
        array.add(bookshelf);
        return bookshelf;
    }

    @Override
    public Bookshelf read(Integer index) {
        return array.get(index);
    }

    @Override
    public void delete(Integer index) {
        array.remove(index);
    }

    @Override
    public Bookshelf update(Bookshelf bookshelf) {
        int index = bookshelf.getId();
        if (array.get(index) != null) {
            array.set(index, bookshelf);
            return bookshelf;
        }
        return null;
    }

    @Override
    public List<Bookshelf> readAll() {
        return array;
    }

    @Override
    public void createAll(List<Bookshelf> bookshelves) {
        this.array = bookshelves;
    }

    private int searchBook(Book book) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getBook().equals(book)) {
                return i;
            }
        }
        return -1;
    }
}
