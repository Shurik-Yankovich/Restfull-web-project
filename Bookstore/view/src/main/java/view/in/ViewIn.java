package view.in;

import entity.Bookshelf;
import entity.Customer;

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
