package bookstore.menu.action.ordermenu;

import bookstore.menu.action.Action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static bookstore.menu.Console.console;
import static bookstore.service.store.BookStoreService.bookstore;

public class CountCompletedOrderAction implements Action {
    @Override
    public void execute() {
        System.out.println("Введите период дат в формате \"дд мм гггг\":");
        String dateFrom = console.readStringFromConsole();
        String dateTo = console.readStringFromConsole();
        int numberComplOrder = bookstore.getCountCompletedOrder(LocalDate.parse(dateFrom, DateTimeFormatter.ofPattern("dd MM yyyy")),
                LocalDate.parse(dateTo, DateTimeFormatter.ofPattern("dd MM yyyy")));
        System.out.println("За данный промежуток времени завершено " + numberComplOrder + " заказов");
    }
}
