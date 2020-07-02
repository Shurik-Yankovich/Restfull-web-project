package bookstore.model.book;

public class ThrillerBookMaker implements BookMaker {
    @Override
    public Book makeBook() {
        return new ThrillerBook();
    }
}
