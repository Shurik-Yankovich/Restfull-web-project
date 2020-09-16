package util.csv;

import entity.*;
import com.annotation.InjectByProperty;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderCsv implements CsvUtil<Order> {

//    @InjectByProperty(propertyName = "ORDER_CSV_FILE_NAME")
    @Value("${ORDER_CSV_FILE_NAME}")
    private String ORDER_CSV_FILE_NAME;

    @Override
    public void writeToCsv(Order order) throws IOException {
        Writer writer = new FileWriter(ORDER_CSV_FILE_NAME, true);
        writer.write(convertOrderToString(order));
        writer.close();
    }

    @Override
    public void writeAllToCsv(List<Order> orderList) throws IOException {
        Writer writer = new FileWriter(ORDER_CSV_FILE_NAME, false);
        StringBuilder text = new StringBuilder();
        for (Order order : orderList) {
            text.append(convertOrderToString(order)).append("\n");
        }
        writer.write(text.toString());
        writer.close();
    }

    @Override
    public Order readFromCsv(int id) throws IOException {
        List<Order> orderList = readAllFromCsv();
        for (Order order : orderList) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public List<Order> readAllFromCsv() throws IOException {
        List<Order> orderList = new ArrayList<>();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(ORDER_CSV_FILE_NAME));
        while ((line = reader.readLine()) != null) {
            Order order = convertStringToOrder(line);
            orderList.add(order);
        }
        reader.close();
        return orderList;
    }

    private String convertOrderToString(Order order) {
        LocalDate date = order.getOrderCompletionDate();
        return String.format("%d;%s;%s#%s#%s;%s;%s;%s;%.2f;%s",
                order.getId(),
                listBookToString(order.getBooks()),
                order.getCustomer().getFullName(),
                order.getCustomer().getAddress(),
                order.getCustomer().getPhoneNumber(),
                listRequestToString(order.getRequests()),
                order.getOrderDate().format(DateTimeFormatter.ofPattern("dd MM yyyy")),
                date != null ? date.format(DateTimeFormatter.ofPattern("dd MM yyyy")) : null,
                order.getPrice(),
                order.getStatus());
    }

    public String listBookToString(List<Book> list) {
        StringBuilder text = new StringBuilder();
        text.append(String.format("%s,%s,%d",
                list.get(0).getTitle(), list.get(0).getAuthor(), list.get(0).getPublicationYear()));
        for (int i = 1; i < list.size(); i++) {
            text.append(String.format("#%s,%s,%d",
                    list.get(i).getTitle(), list.get(i).getAuthor(), list.get(i).getPublicationYear()));
        }
        return text.toString();
    }

    public String listRequestToString(List<Request> list) {
        if (list.size() > 0) {
            StringBuilder text = new StringBuilder();
//            text.append(String.format("%d", list.get(0)));
            text.append(convertRequestToString(list.get(0)));
            for (int i = 1; i < list.size(); i++) {
//                text.append(String.format(",%d", list.get(i)));
                text.append(convertRequestToString(list.get(i)));
            }
            return text.toString();
        }
        return null;
    }

    private String convertRequestToString(Request request) {
        return String.format("%d,%s,%s,%d,%d,%s",
                request.getId(),
                request.getBook().getTitle(),
                request.getBook().getAuthor(),
                request.getBook().getPublicationYear(),
                request.getCount(),
                request.getStatus());
    }

    private Order convertStringToOrder(String text) {
        final String regex = ";";
        String[] values = text.split(regex);
        Order order = new Order();
        order.setId(Integer.parseInt(values[0]));
        order.setBooks(stringToListBook(values[1]));
        order.setCustomer(convertStringToCustomer(values[2]));
        order.setRequests(stringToListRequest(values[3]));
        order.setOrderDate(LocalDate.parse(values[4], DateTimeFormatter.ofPattern("dd MM yyyy")));
        if (!values[5].equals("null")) {
            order.setOrderCompletionDate(LocalDate.parse(values[5], DateTimeFormatter.ofPattern("dd MM yyyy")));
        }
        order.setPrice(Double.parseDouble(values[6]));
        order.setStatus(Status.valueOf(values[7]));
        return order;
    }

    public List<Book> stringToListBook(String text) {
        final String regex = "#";
        List<Book> bookList = new ArrayList<>();
        String[] values = text.split(regex);
        for (String value : values) {
            bookList.add(convertStringToBook(value));
        }
        return bookList;
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

    private Customer convertStringToCustomer(String text) {
        final String regex = "#";
        Customer customer = new Customer();
        String[] values = text.split(regex);
        customer.setFullName(values[0]);
        customer.setAddress(values[1]);
        customer.setPhoneNumber(values[2]);
        return customer;
    }

    public List<Request> stringToListRequest(String text) {
        if (!text.equals("null")) {
            final String regex = ",";
            List<Request> list = new ArrayList<>();
            String[] values = text.split(regex);
            for (String value : values) {
//                list.add(Integer.parseInt(value));
                list.add(convertStringToRequest(value));
            }
            return list;
        }
        return new ArrayList<>();
    }

    private Request convertStringToRequest(String text) {
        final String regex = ",";
        String[] values = text.split(regex);
        Request request = new Request();
        Book book = new Book();
        request.setId(Integer.parseInt(values[0]));
        book.setTitle(values[1]);
        book.setAuthor(values[2]);
        book.setPublicationYear(Integer.parseInt(values[3]));
        request.setBook(book);
        request.setCount(Integer.parseInt(values[4]));
        request.setStatus(Status.valueOf(values[5]));
        return request;
    }
}
