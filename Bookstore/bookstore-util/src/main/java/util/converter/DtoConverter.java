package util.converter;

import dto.*;
import entity.*;

import java.util.stream.Collectors;

public class DtoConverter {

    public static BookDto convertBookToDto(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setAuthor(book.getAuthor());
        bookDto.setTitle(book.getTitle());
        bookDto.setPublicationYear(book.getPublicationYear());
        return bookDto;
    }

    public static BookshelfDto convertBookshelfToDto(Bookshelf bookshelf){
        BookshelfDto bookshelfDto = new BookshelfDto();
        bookshelfDto.setId(bookshelf.getId());
        bookshelfDto.setBook(convertBookToDto(bookshelf.getBook()));
        bookshelfDto.setCount(bookshelf.getCount());
        bookshelfDto.setPrice(bookshelf.getPrice());
        bookshelfDto.setArrivalDate(bookshelf.getArrivalDate());
        return bookshelfDto;
    }

    public static CustomerDto convertCustomerToDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFullName(customer.getFullName());
        customerDto.setAddress(customer.getAddress());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        return customerDto;
    }

    public static RequestDto convertRequestToDto(Request request){
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setBook(convertBookToDto(request.getBook()));
        requestDto.setStatus(request.getStatus());
        return requestDto;
    }

    public static OrderDto convertOrderToDto(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCustomer(convertCustomerToDto(order.getCustomer()));
        orderDto.setPrice(order.getPrice());
        orderDto.setOrderCompletionDate(order.getOrderCompletionDate());
        orderDto.setStatus(order.getStatus());
        orderDto.setBooks(order.getBooks().stream().map(s->convertBookToDto(s)).collect(Collectors.toList()));
        orderDto.setRequests(order.getRequests().stream().map(s->convertRequestToDto(s)).collect(Collectors.toList()));
        return orderDto;
    }

}
