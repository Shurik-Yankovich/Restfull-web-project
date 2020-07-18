package bookstore.util.csv;

import bookstore.entity.Customer;
import bookstore.entity.Order;
import bookstore.entity.Status;
import bookstore.entity.book.Book;
import bookstore.entity.book.NovelBook;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderCsv implements CsvUtil<Order> {

    private static final String ROOT_DIR_PATH = ".\\src\\bookstore\\rootdir\\order.csv";

    @Override
    public void writeToCsv(Order order) throws IOException {
        Writer writer = new FileWriter(ROOT_DIR_PATH, true);
        writer.write(convertOrderToString(order));
    }

    @Override
    public void writeAllToCsv(List<Order> orderList) throws IOException {
        Writer writer = new FileWriter(ROOT_DIR_PATH, false);
        StringBuilder text = new StringBuilder();
        for (Order order : orderList) {
            text.append(convertOrderToString(order)).append("\n");
        }
        writer.write(text.toString());
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
        BufferedReader reader = new BufferedReader(new FileReader(ROOT_DIR_PATH));
        while ((line = reader.readLine()) != null) {
            Order order = convertStringToOrder(line);
            orderList.add(order);
        }
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
                listIntegerToString(order.getNumbersRequest()),
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

    public String listIntegerToString(List<Integer> list) {
        if (list.size() > 0) {
            StringBuilder text = new StringBuilder();
            text.append(String.format("%d", list.get(0)));
            for (int i = 1; i < list.size(); i++) {
                text.append(String.format(",%d", list.get(i)));
            }
            return text.toString();
        }
        return null;
    }

    private Order convertStringToOrder(String text) {
        final String regex = ";";
        String[] values = text.split(regex);
        Order order = new Order();
        order.setId(Integer.parseInt(values[0]));
        order.setBooks(stringToListBook(values[1]));
        order.setCustomer(convertStringToCustomer(values[2]));
        order.setNumbersRequest(stringToListInteger(values[3]));
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
        Book book = new NovelBook();
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

    public List<Integer> stringToListInteger(String text) {
        if (!text.equals("null")) {
            final String regex = ",";
            List<Integer> list = new ArrayList<>();
            String[] values = text.split(regex);
            for (String value : values) {
                list.add(Integer.parseInt(value));
            }
            return list;
        }
        return new ArrayList<>();
    }
}
