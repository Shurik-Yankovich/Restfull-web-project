package util.converter;

import dto.BookDto;
import dto.BookshelfDto;
import dto.CustomerDto;
import entity.Book;
import entity.Bookshelf;
import entity.Customer;

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
        return customerDto;
    }
}
