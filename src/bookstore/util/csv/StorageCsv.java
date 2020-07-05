package bookstore.util.csv;

import bookstore.entity.Bookshelf;
import bookstore.entity.book.Book;
import bookstore.entity.book.NovelBook;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StorageCsv implements CsvUtil<Bookshelf> {

    private static final String ROOT_DIR_PATH = ".\\src\\bookstore\\rootdir\\storage.csv";

    @Override
    public void writeToCsv(Bookshelf bookshelf) {
        try (Writer writer = new FileWriter(ROOT_DIR_PATH, true)) {
            writer.write(convertBookshelfToString(bookshelf));
            System.out.println("Книга №" + bookshelf.getId() + " был добавлен в файл storage.csv");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void writeAllToCsv(List<Bookshelf> bookshelves) {
        try (Writer writer = new FileWriter(ROOT_DIR_PATH, false)) {
            StringBuilder text = new StringBuilder();
            for (Bookshelf bookshelf : bookshelves) {
                text.append(convertBookshelfToString(bookshelf)).append("\n");
            }
            writer.write(text.toString());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Bookshelf readFromCsv(int id) {
        List<Bookshelf> bookshelves = readAllFromCsv();
        for (Bookshelf bookshelf: bookshelves) {
            if (bookshelf.getId() == id) {
                return bookshelf;
            }
        }
        return null;
    }

    @Override
    public List<Bookshelf> readAllFromCsv() {
        List<Bookshelf> bookshelves = new ArrayList<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(ROOT_DIR_PATH))) {
            while ((line = reader.readLine()) != null) {
                Bookshelf bookshelf = convertStringToBookshelf(line);
                bookshelves.add(bookshelf);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
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
        Book book = new NovelBook();
        String[] values = text.split(regex);
        book.setTitle(values[0]);
        book.setAuthor(values[1]);
        book.setPublicationYear(Integer.parseInt(values[2]));
        return book;
    }

}
