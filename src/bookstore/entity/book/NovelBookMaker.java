package bookstore.entity.book;

public class NovelBookMaker implements BookMaker {
    @Override
    public Book makeBook() {
        return new NovelBook();
    }
}
