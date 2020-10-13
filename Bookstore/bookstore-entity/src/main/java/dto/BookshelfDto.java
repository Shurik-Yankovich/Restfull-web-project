package dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class BookshelfDto {

    private int id;
    private BookDto book;
    private int count;
    private double price;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate arrivalDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
