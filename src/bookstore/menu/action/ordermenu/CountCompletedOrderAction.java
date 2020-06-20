package bookstore.menu.action.ordermenu;

import bookstore.menu.action.Action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.order.BookOrderService.ORDER_SERVICE;

public class CountCompletedOrderAction implements Action {
    @Override
    public void execute() {
        System.out.println("Введите период дат в формате \"дд мм гггг\":");
        String dateFrom = CONSOLE_WORKER.readStringFromConsole();
        String dateTo = CONSOLE_WORKER.readStringFromConsole();
        int numberComplOrder = ORDER_SERVICE.getCountCompletedOrder(LocalDate.parse(dateFrom, DateTimeFormatter.ofPattern("dd MM yyyy")),
                LocalDate.parse(dateTo, DateTimeFormatter.ofPattern("dd MM yyyy")));
        System.out.println("За данный промежуток времени завершено " + numberComplOrder + " заказов");
    }
}
