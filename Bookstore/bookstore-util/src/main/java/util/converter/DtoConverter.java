package util.converter;

import dto.*;
import entity.*;

import java.util.stream.Collectors;

public class DtoConverter {

    public static BookDto convertBookToDto(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setTitle(book.getTitle());
        bookDto.setPublicationYear(book.getPublicationYear());
        return bookDto;
    }

    public static Book convertDtoToBook(BookDto bookDto){
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setPublicationYear(bookDto.getPublicationYear());
        return book;
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

    public static Bookshelf convertDtoToBookshelf(BookshelfDto bookshelfDto){
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setId(bookshelfDto.getId());
        bookshelf.setBook(convertDtoToBook(bookshelfDto.getBook()));
        bookshelf.setCount(bookshelfDto.getCount());
        bookshelf.setPrice(bookshelfDto.getPrice());
        bookshelf.setArrivalDate(bookshelfDto.getArrivalDate());
        return bookshelf;
    }

    public static CustomerDto convertCustomerToDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFullName(customer.getFullName());
        customerDto.setAddress(customer.getAddress());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        return customerDto;
    }

    public static Customer convertDtoToCustomer(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setFullName(customerDto.getFullName());
        customer.setAddress(customerDto.getAddress());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        return customer;
    }

    public static RequestDto convertRequestToDto(Request request){
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setBook(convertBookToDto(request.getBook()));
        requestDto.setStatus(request.getStatus());
        return requestDto;
    }

    public static Request convertDtoToRequest(RequestDto requestDto){
        Request request = new Request();
        request.setId(requestDto.getId());
        request.setBook(convertDtoToBook(requestDto.getBook()));
        request.setStatus(requestDto.getStatus());
        return request;
    }

    public static OrderDto convertOrderToDto(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCustomer(convertCustomerToDto(order.getCustomer()));
        orderDto.setPrice(order.getPrice());
        orderDto.setOrderCompletionDate(order.getOrderCompletionDate());
        orderDto.setStatus(order.getStatus());
        orderDto.setBooks(order.getBooks().stream().map(DtoConverter::convertBookToDto).collect(Collectors.toList()));
        orderDto.setRequests(order.getRequests().stream().map(DtoConverter::convertRequestToDto).collect(Collectors.toList()));
        return orderDto;
    }

    public static Order convertDtoToOrder(OrderDto orderDto){
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setCustomer(convertDtoToCustomer(orderDto.getCustomer()));
        order.setPrice(orderDto.getPrice());
        order.setOrderCompletionDate(orderDto.getOrderCompletionDate());
        order.setStatus(orderDto.getStatus());
        order.setBooks(orderDto.getBooks().stream().map(DtoConverter::convertDtoToBook).collect(Collectors.toList()));
        order.setRequests(orderDto.getRequests().stream().map(DtoConverter::convertDtoToRequest).collect(Collectors.toList()));
        return order;
    }

}
