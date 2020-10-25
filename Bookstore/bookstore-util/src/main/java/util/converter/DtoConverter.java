package util.converter;

import dto.*;
import entity.*;
import entity.security.User;

import java.util.stream.Collectors;

public class DtoConverter {

    public static BookDto convertBookToDto(Book book) {
        if (book != null) {
            BookDto bookDto = new BookDto();
            bookDto.setId(book.getId());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setTitle(book.getTitle());
            bookDto.setPublicationYear(book.getPublicationYear());
            return bookDto;
        }
        return null;
    }

    public static Book convertDtoToBook(BookDto bookDto) {
        if (bookDto != null) {
            Book book = new Book();
            book.setId(bookDto.getId());
            book.setAuthor(bookDto.getAuthor());
            book.setTitle(bookDto.getTitle());
            book.setPublicationYear(bookDto.getPublicationYear());
            return book;
        }
        return null;
    }

    public static BookshelfDto convertBookshelfToDto(Bookshelf bookshelf) {
        if (bookshelf != null) {
            BookshelfDto bookshelfDto = new BookshelfDto();
            bookshelfDto.setId(bookshelf.getId());
            bookshelfDto.setBook(convertBookToDto(bookshelf.getBook()));
            bookshelfDto.setCount(bookshelf.getCount());
            bookshelfDto.setPrice(bookshelf.getPrice());
            bookshelfDto.setArrivalDate(bookshelf.getArrivalDate());
            return bookshelfDto;
        }
        return null;
    }

    public static Bookshelf convertDtoToBookshelf(BookshelfDto bookshelfDto) {
        if (bookshelfDto != null) {
            Bookshelf bookshelf = new Bookshelf();
            bookshelf.setId(bookshelfDto.getId());
            bookshelf.setBook(convertDtoToBook(bookshelfDto.getBook()));
            bookshelf.setCount(bookshelfDto.getCount());
            bookshelf.setPrice(bookshelfDto.getPrice());
            bookshelf.setArrivalDate(bookshelfDto.getArrivalDate());
            return bookshelf;
        }
        return null;
    }

    public static CustomerDto convertCustomerToDto(Customer customer) {
        if (customer != null) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setFullName(customer.getFullName());
            customerDto.setAddress(customer.getAddress());
            customerDto.setPhoneNumber(customer.getPhoneNumber());
            return customerDto;
        }
        return null;
    }

    public static Customer convertDtoToCustomer(CustomerDto customerDto) {
        if (customerDto != null) {
            Customer customer = new Customer();
            customer.setFullName(customerDto.getFullName());
            customer.setAddress(customerDto.getAddress());
            customer.setPhoneNumber(customerDto.getPhoneNumber());
            return customer;
        }
        return null;
    }

    public static RequestDto convertRequestToDto(Request request) {
        if (request != null) {
            RequestDto requestDto = new RequestDto();
            requestDto.setId(request.getId());
            requestDto.setBook(convertBookToDto(request.getBook()));
            requestDto.setStatus(request.getStatus());
            return requestDto;
        }
        return null;
    }

    public static Request convertDtoToRequest(RequestDto requestDto) {
        if (requestDto != null) {
            Request request = new Request();
            request.setId(requestDto.getId());
            request.setBook(convertDtoToBook(requestDto.getBook()));
            request.setStatus(requestDto.getStatus());
            return request;
        }
        return null;
    }

    public static OrderDto convertOrderToDto(Order order) {
        if (order != null) {
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
        return null;
    }

    public static Order convertDtoToOrder(OrderDto orderDto) {
        if (orderDto != null) {
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
        return null;
    }

    public static UserDto convertUserToDto(User user) {
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setUsername(user.getUsername());
            userDto.setPassword(user.getPassword());
            userDto.setPasswordConfirm(user.getPasswordConfirm());
            return userDto;
        }
        return null;
    }

    public static User convertDtoToUser(UserDto userDto) {
        if (userDto != null) {
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            user.setPasswordConfirm(userDto.getPasswordConfirm());
//            Set<Role> roles = new HashSet<>();
//            roles.add(new Role(2, "ROLE_USER"));
//            user.setRoles(roles);
            return user;
        }
        return null;
    }

}
