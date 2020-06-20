package bookstore.model.book;

public class NovelBookMaker implements BookMaker {
    @Override
    public Book makeBook() {
        return new NovelBook();
    }
}
