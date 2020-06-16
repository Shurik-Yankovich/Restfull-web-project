package electronicbookstore.menu.action.ordermenu;

import electronicbookstore.menu.action.Action;
import electronicbookstore.model.Book;
import electronicbookstore.model.Bookshelf;
import electronicbookstore.model.Customer;
import electronicbookstore.model.Order;

import java.util.ArrayList;
import java.util.List;

import static electronicbookstore.menu.Console.console;
import static electronicbookstore.service.store.ElectronicBookstore.bookstore;

public class AddOrderAction implements Action {
    @Override
    public void execute() {
        bookstore.addOrder(createOrder());
    }

    private Order createOrder() {
        System.out.println("Введите ваше ФИО:");
        String name = console.readStringFromConsole();
        System.out.println("Введите ваш адрес:");
        String address = console.readStringFromConsole();
        System.out.println("Введите номер телефона:");
        String phone = console.readStringFromConsole();
        Customer customer = new Customer(name, phone, address);
        List<Book> booksInOrder = new ArrayList<>();

        List<Bookshelf> bookshelves = bookstore.getBookList();
        console.printList(bookshelves);
        System.out.println("Выбирете книги из списка (для завершения формирования списка введите -1):");
        int bookNumber = console.readIntFromConsole();
        while (bookNumber != -1) {
            if (bookNumber >= 0 && bookNumber < bookshelves.size()) {
                booksInOrder.add(bookshelves.get(bookNumber).getBook());
                bookNumber = console.readIntFromConsole();
            }
        }

        return new Order(customer, booksInOrder);
    }
}
