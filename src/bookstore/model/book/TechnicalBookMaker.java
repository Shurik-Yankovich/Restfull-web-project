package bookstore.model.book;

public class TechnicalBookMaker implements BookMaker {
    @Override
    public Book makeBook() {
        return new TechnicalBook();
    }
}
