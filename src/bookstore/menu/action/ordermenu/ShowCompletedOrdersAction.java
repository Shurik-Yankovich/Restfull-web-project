package bookstore.menu.action.ordermenu;

import bookstore.menu.action.Action;
import bookstore.model.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static bookstore.menu.Console.console;
import static bookstore.service.store.ElectronicBookstore.bookstore;

public class ShowCompletedOrdersAction implements Action {
    @Override
    public void execute() {
        System.out.println("Введите период дат в формате \"дд мм гггг\":");
        String dateFrom = console.readStringFromConsole();
        String dateTo = console.readStringFromConsole();
        List<Order> orders = bookstore.getCompletedOrderList(LocalDate.parse(dateFrom, DateTimeFormatter.ofPattern("dd MM yyyy")),
                LocalDate.parse(dateTo, DateTimeFormatter.ofPattern("dd MM yyyy")));
        console.printList(orders);
    }
}
