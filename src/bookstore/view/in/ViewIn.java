package bookstore.view.in;

import bookstore.entity.Bookshelf;
import bookstore.entity.Customer;

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
    int saveChanging();
}
