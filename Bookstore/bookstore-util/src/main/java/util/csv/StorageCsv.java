package util.csv;

import entity.Bookshelf;
import entity.Book;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StorageCsv implements CsvUtil<Bookshelf> {

//    @Value("${STORAGE_CSV_FILE_NAME}")
    private String STORAGE_CSV_FILE_NAME = "rootdir/storage.csv";

    @Override
    public void writeToCsv(Bookshelf bookshelf) throws IOException {
        Writer writer = new FileWriter(STORAGE_CSV_FILE_NAME, true);
        writer.write(convertBookshelfToString(bookshelf));
        writer.close();
    }

    @Override
    public void writeAllToCsv(List<Bookshelf> bookshelves) throws IOException {
        Writer writer = new FileWriter(STORAGE_CSV_FILE_NAME, false);
        StringBuilder text = new StringBuilder();
        for (Bookshelf bookshelf : bookshelves) {
            text.append(convertBookshelfToString(bookshelf)).append("\n");
        }
        writer.write(text.toString());
        writer.close();
    }

    @Override
    public Bookshelf readFromCsv(int id) throws IOException {
        List<Bookshelf> bookshelves = readAllFromCsv();
        for (Bookshelf bookshelf : bookshelves) {
            if (bookshelf.getId() == id) {
                return bookshelf;
            }
        }
        return null;
    }

    @Override
    public List<Bookshelf> readAllFromCsv() throws IOException {
        List<Bookshelf> bookshelves = new ArrayList<>();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(STORAGE_CSV_FILE_NAME));
        while ((line = reader.readLine()) != null) {
            Bookshelf bookshelf = convertStringToBookshelf(line);
            bookshelves.add(bookshelf);
        }
        reader.close();
        return bookshelves;
    }

    private String convertBookshelfToString(Bookshelf bookshelf) {
        return String.format("%d;%s,%s,%d;%d;%.3f;%s",
                bookshelf.getId(),
                bookshelf.getBook().getTitle(),
                bookshelf.getBook().getAuthor(),
                bookshelf.getBook().getPublicationYear(),
                bookshelf.getCount(),
                bookshelf.getPrice(),
                bookshelf.getArrivalDate().format(DateTimeFormatter.ofPattern("dd MM yyyy")));
    }

    private Bookshelf convertStringToBookshelf(String text) {
        final String regex = ";";
        String[] values = text.split(regex);
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setId(Integer.parseInt(values[0]));
        bookshelf.setBook(convertStringToBook(values[1]));
        bookshelf.setCount(Integer.parseInt(values[2]));
        bookshelf.setPrice(Double.parseDouble(values[3]));
        bookshelf.setArrivalDate(LocalDate.parse(values[4], DateTimeFormatter.ofPattern("dd MM yyyy")));
        return bookshelf;
    }

    private Book convertStringToBook(String text) {
        final String regex = ",";
        Book book = new Book();
        String[] values = text.split(regex);
        book.setTitle(values[0]);
        book.setAuthor(values[1]);
        book.setPublicationYear(Integer.parseInt(values[2]));
        return book;
    }

}
