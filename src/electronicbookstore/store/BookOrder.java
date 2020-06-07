package electronicbookstore.store;

import electronicbookstore.storage.Book;

import java.util.Arrays;
import java.util.Calendar;

import static electronicbookstore.store.Status.NEW;

public class BookOrder {

    private Book[] books;
    private Customer customer;
    private Calendar orderDate;
    private Status status;
    private Calendar orderCompletionDate;
    private double price;

    public BookOrder(Customer customer, Calendar orderDate, Book... books) {
        this.books = books;
        this.customer = customer;
        this.orderDate = orderDate;
        this.status = NEW;
    }

    public Book[] getBooks() {
        return books;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Calendar getOrderDate() {
        return orderDate;
    }

    public Status getStatus() {
        return status;
    }

    public Calendar getOrderCompletionDate() {
        return orderCompletionDate;
    }

    public double getPrice() {
        return price;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setOrderCompletionDate(Calendar orderCompletionDate) {
        this.orderCompletionDate = orderCompletionDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean equals(BookOrder order) {
        return customer.equals(order.getCustomer()) &&
                orderDate.equals(order.getOrderDate()) &&
                Arrays.equals(books, order.getBooks());
    }

    @Override
    public String toString() {
        return "BookOrder{" +
                "books=" + Arrays.toString(books) +
                ", customer=" + customer +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", orderCompletionDate=" + orderCompletionDate +
                '}';
    }
}
