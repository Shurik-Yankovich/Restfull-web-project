package bookstore.entity.book;

public interface Book {

    String getTitle();
    String getAuthor();
    int getPublicationYear();
    void setTitle(String title);
    void setAuthor(String author);
    void setPublicationYear(int publicationYear);
}
