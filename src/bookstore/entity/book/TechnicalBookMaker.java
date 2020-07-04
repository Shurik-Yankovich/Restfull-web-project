package bookstore.entity.book;

public class TechnicalBookMaker implements BookMaker {
    @Override
    public Book makeBook() {
        return new TechnicalBook();
    }
}
