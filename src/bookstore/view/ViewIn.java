package bookstore.view;

import bookstore.model.Bookshelf;
import bookstore.model.Customer;

import java.time.LocalDate;
import java.util.List;

public interface ViewIn {

    LocalDate readDateFrom();
    LocalDate readDateTo();
    Customer readCustomer();
    List<Integer> readBookList(List<Bookshelf> bookshelves);
    <T> T choiceFromList(List<T> list);
    int readCountBook();
    int readIntFromConsole();
}
