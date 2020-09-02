package bookstore.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static bookstore.entity.Status.NEW;

public class Order implements Serializable {

    private int id;
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

    public Order() {
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setNumbersRequest(List<Integer> numbersRequest) {
        this.numbersRequest = numbersRequest;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
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
        return String.format("Order:\nBooks: %s\n%sorderDate = %s, status - %s, orderCompletionDate = %s, price = %.2f\n",
                books.toString(),
                customer,
                orderDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                status,
                (orderCompletionDate != null ? orderCompletionDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) : null),
                price);
    }
}
