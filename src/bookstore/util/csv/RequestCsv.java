package bookstore.util.csv;

import bookstore.entity.Request;
import bookstore.entity.Status;
import bookstore.entity.book.Book;
import bookstore.entity.book.NovelBook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static bookstore.constant.FileName.REQUEST_CSV_FILE_NAME;

public class RequestCsv implements CsvUtil<Request> {

    @Override
    public void writeToCsv(Request request) throws IOException {
        Writer writer = new FileWriter(REQUEST_CSV_FILE_NAME, true);
        writer.write(convertRequestToString(request));
    }

    @Override
    public void writeAllToCsv(List<Request> requestList) throws IOException {
        Writer writer = new FileWriter(REQUEST_CSV_FILE_NAME, false);
        StringBuilder text = new StringBuilder();
        for (Request request : requestList) {
            text.append(convertRequestToString(request)).append("\n");
        }
        writer.write(text.toString());
    }

    @Override
    public Request readFromCsv(int id) throws IOException {
        List<Request> requestList = readAllFromCsv();
        for (Request request : requestList) {
            if (request.getId() == id) {
                return request;
            }
        }
        return null;
    }

    @Override
    public List<Request> readAllFromCsv() throws IOException {
        List<Request> requestList = new ArrayList<>();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(REQUEST_CSV_FILE_NAME));
        while ((line = reader.readLine()) != null) {
            Request request = convertStringToRequest(line);
            requestList.add(request);
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

    private Request convertStringToRequest(String text) {
        final String regex = ";";
        String[] values = text.split(regex);
        Request request = new Request();
        request.setId(Integer.parseInt(values[0]));
        request.setBook(convertStringToBook(values[1]));
        request.setCount(Integer.parseInt(values[2]));
        request.setStatus(Status.valueOf(values[3]));
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
