package electronicbookstore.menu.action.ordermenu;

import electronicbookstore.menu.action.Action;
import electronicbookstore.model.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static electronicbookstore.menu.Console.console;
import static electronicbookstore.service.store.ElectronicBookstore.bookstore;

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
