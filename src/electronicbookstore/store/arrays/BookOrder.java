package electronicbookstore.store.arrays;

import electronicbookstore.storage.Book;
import electronicbookstore.store.Customer;
import electronicbookstore.store.Status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static electronicbookstore.store.Status.NEW;

public class BookOrder {

    private Book[] books;
    private Customer customer;
    private int[] numbersRequest;
    private Calendar orderDate;
    private Calendar orderCompletionDate;
    private double price;
    private Status status;

    public BookOrder(Customer customer, Book... books) {
        this.books = books;
        this.customer = customer;
        this.orderDate = new GregorianCalendar();
        this.status = NEW;
    }

    public Book[] getBooks() {
        return books;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int[] getNumbersRequest() {
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

    public void setNumbersRequest(int[] numbersRequest) {
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

    public boolean equals(BookOrder order) {
        return customer.equals(order.getCustomer()) &&
                orderDate.equals(order.getOrderDate()) &&
                Arrays.equals(books, order.getBooks());
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        return String.format("Order:\nBooks: %s\n%sorderDate = %s, status - %b, orderCompletionDate = %s, price = %.2f",
                Arrays.toString(books), customer, df.format(orderDate.getTime()), status,
                (orderCompletionDate != null ? df.format(orderCompletionDate.getTime()) : null), price);
    }
}
