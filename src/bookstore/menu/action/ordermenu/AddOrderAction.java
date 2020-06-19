package bookstore.menu.action.ordermenu;

import bookstore.menu.action.Action;
import bookstore.model.book.Book;
import bookstore.model.Bookshelf;
import bookstore.model.Customer;
import bookstore.model.Order;

import java.util.ArrayList;
import java.util.List;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.store.BookStoreService.bookstore;

public class AddOrderAction implements Action {
    @Override
    public void execute() {
        bookstore.addOrder(createOrder());
    }

    private Order createOrder() {
        System.out.println("Введите ваше ФИО:");
        String name = CONSOLE_WORKER.readStringFromConsole();
        System.out.println("Введите ваш адрес:");
        String address = CONSOLE_WORKER.readStringFromConsole();
        System.out.println("Введите номер телефона:");
        String phone = CONSOLE_WORKER.readStringFromConsole();
        Customer customer = new Customer(name, phone, address);
        List<Book> booksInOrder = new ArrayList<>();

        List<Bookshelf> bookshelves = bookstore.getBookList();
        CONSOLE_WORKER.printList(bookshelves);
        System.out.println("Выбирете книги из списка (для завершения формирования списка введите -1):");
        int bookNumber = CONSOLE_WORKER.readIntFromConsole();
        while (bookNumber != -1) {
            if (bookNumber >= 0 && bookNumber < bookshelves.size()) {
                booksInOrder.add(bookshelves.get(bookNumber).getBook());
                bookNumber = CONSOLE_WORKER.readIntFromConsole();
            }
        }

        return new Order(customer, booksInOrder);
    }
}
