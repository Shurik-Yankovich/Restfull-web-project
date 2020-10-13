package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import entity.Status;
import java.time.LocalDate;
import java.util.List;

public class OrderDto {

    private int id;
    private List<BookDto> books;
    private CustomerDto customer;
    private List<RequestDto> requests;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate orderCompletionDate;
    private double price;
    private Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<BookDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public List<RequestDto> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestDto> requests) {
        this.requests = requests;
    }

    public LocalDate getOrderCompletionDate() {
        return orderCompletionDate;
    }

    public void setOrderCompletionDate(LocalDate orderCompletionDate) {
        this.orderCompletionDate = orderCompletionDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
