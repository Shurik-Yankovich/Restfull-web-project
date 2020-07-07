package bookstore.util.csv;

import bookstore.exeption.StatusException;
import bookstore.entity.Request;
import bookstore.entity.Status;
import bookstore.entity.book.Book;
import bookstore.entity.book.NovelBook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RequestCsv implements CsvUtil<Request> {

    private static final String ROOT_DIR_PATH = ".\\src\\bookstore\\rootdir\\request.csv";

    @Override
    public void writeToCsv(Request request) {
        try (Writer writer = new FileWriter(ROOT_DIR_PATH, true)) {
            writer.write(convertRequestToString(request));
            System.out.println("Запрос №" + request.getId() + " был добавлен в файл request.csv");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void writeAllToCsv(List<Request> requestList) {
        try (Writer writer = new FileWriter(ROOT_DIR_PATH, false)) {
            StringBuilder text = new StringBuilder();
            for (Request request : requestList) {
                text.append(convertRequestToString(request)).append("\n");
            }
            writer.write(text.toString());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Request readFromCsv(int id) {
        List<Request> requestList = readAllFromCsv();
        for (Request request : requestList) {
            if (request.getId() == id) {
                return request;
            }
        }
        return null;
    }

    @Override
    public List<Request> readAllFromCsv() {
        List<Request> requestList = new ArrayList<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(ROOT_DIR_PATH))) {
            while ((line = reader.readLine()) != null) {
                Request request = convertStringToRequest(line);
                requestList.add(request);
            }
        } catch (StatusException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return requestList;
    }

    private String convertRequestToString(Request request) {
        return String.format("%d;%s,%s,%d;%d;%s",
                request.getId(),
                request.getBook().getTitle(),
                request.getBook().getAuthor(),
                request.getBook().getPublicationYear(),
                request.getCount(),
                request.getStatus());
    }

    private Request convertStringToRequest(String text) throws StatusException {
        final String regex = ";";
        String[] values = text.split(regex);
        Request request = new Request();
        request.setId(Integer.parseInt(values[0]));
        request.setBook(convertStringToBook(values[1]));
        request.setCount(Integer.parseInt(values[2]));
        request.setStatus(Status.getStatus(values[3]));
        return request;
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
