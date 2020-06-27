package bookstore.model.book;

public enum BookFactory {
    NOVEL(new NovelBookMaker()),
    TECHNICAL(new TechnicalBookMaker()),
    THRILLER(new ThrillerBookMaker());

    private BookMaker bookMaker;

    BookFactory(BookMaker bookMaker) {
        this.bookMaker = bookMaker;
    }

    public Book createBook(){
        return bookMaker.makeBook();
    }
}
