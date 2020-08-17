package bookstore.entity.book;

import java.io.Serializable;

public interface Book extends Serializable {

    String getTitle();
    String getAuthor();
    int getPublicationYear();
    void setTitle(String title);
    void setAuthor(String author);
    void setPublicationYear(int publicationYear);
}
