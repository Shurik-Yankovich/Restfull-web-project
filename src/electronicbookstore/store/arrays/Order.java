package electronicbookstore.store.arrays;

import electronicbookstore.storage.Book;
import electronicbookstore.store.Customer;
import electronicbookstore.store.Status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static electronicbookstore.store.Status.NEW;

public class Order {

    private List<Book> books;
    private Customer customer;
    private List<Integer> numbersRequest;
    private Calendar orderDate;
    private Calendar orderCompletionDate;
    private double price;
    private Status status;

    public Order(Customer customer, Book... books) {
        this.books = Arrays.asList(books);
        this.customer = customer;
        this.orderDate = new GregorianCalendar();
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

    public Calendar getOrderDate() {
        return orderDate;
    }

    public Calendar getOrderCompletionDate() {
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

    public void setOrderCompletionDate(Calendar orderCompletionDate) {
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
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        return String.format("Order:\nBooks: %s\n%sorderDate = %s, status - %b, orderCompletionDate = %s, price = %.2f",
                books.toString(), customer, df.format(orderDate.getTime()), status,
                (orderCompletionDate != null ? df.format(orderCompletionDate.getTime()) : null), price);
    }
}
