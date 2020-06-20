package bookstore.menu.action.ordermenu;

import bookstore.menu.action.Action;
import bookstore.model.Bookshelf;
import bookstore.model.Customer;
import bookstore.model.Order;
import bookstore.model.book.Book;

import java.util.ArrayList;
import java.util.List;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.order.BookOrderService.ORDER_SERVICE;
import static bookstore.service.storage.BookStorageService.STORAGE_SERVICE;

public class AddOrderAction implements Action {
    @Override
    public void execute() {
        ORDER_SERVICE.addOrder(createOrder());
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

        List<Bookshelf> bookshelves = STORAGE_SERVICE.getBookshelfList();
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
