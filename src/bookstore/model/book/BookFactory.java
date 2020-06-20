package bookstore.model.book;

public class BookFactory {

    public static Book getBookByGenre(String genre) {
        BookMaker maker = getMakerByGenre(genre);
        return maker.makeBook();
    }

    private static BookMaker getMakerByGenre(String genre) {
        switch (genre) {
            case "Novel":
                return new NovelBookMaker();
            case "Technical":
                return new TechnicalBookMaker();
            case "Thriller":
                return new ThrillerBookMaker();
            default:
                throw new RuntimeException("Не поддерживаемый жанр книг " + genre);
        }
    }
}
