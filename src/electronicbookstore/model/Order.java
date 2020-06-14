package electronicbookstore.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static electronicbookstore.model.Status.NEW;

public class Order {

    private List<Book> books;
    private Customer customer;
    private List<Integer> numbersRequest;
    private LocalDate orderDate;
    private LocalDate orderCompletionDate;
    private double price;
    private Status status;

    public Order(Customer customer, Book... books) {
        this.books = Arrays.asList(books);
        this.customer = customer;
        this.orderDate = LocalDate.now();
        this.status = NEW;
    }

    public Order(Customer customer, List<Book> books) {
        this.books = books;
        this.customer = customer;
        this.orderDate = LocalDate.now();
        this.status = NEW;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Integer> getNumbersRequest() {
        return numbersRequest;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDate getOrderCompletionDate() {
        return orderCompletionDate;
    }

    public double getPrice() {
        return price;
    }

    public Status getStatus() {
        return status;
    }

    public void setNumbersRequest(List<Integer> numbersRequest) {
        this.numbersRequest = numbersRequest;
    }

    public void setOrderCompletionDate(LocalDate orderCompletionDate) {
        this.orderCompletionDate = orderCompletionDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean equals(Order order) {
        return customer.equals(order.getCustomer()) &&
                orderDate.equals(order.getOrderDate()) &&
                books.equals(order.getBooks());
    }

    @Override
    public String toString() {
        return String.format("Order:\nBooks: %s\n%sorderDate = %s, status - %b, orderCompletionDate = %s, price = %.2f",
                books.toString(),
                customer,
                orderDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                status,
                (orderCompletionDate != null ? orderCompletionDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) : null),
                price);
    }
}
