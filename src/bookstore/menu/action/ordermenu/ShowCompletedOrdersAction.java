package bookstore.menu.action.ordermenu;

import bookstore.menu.action.Action;
import bookstore.model.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.order.BookOrderService.ORDER_SERVICE;

public class ShowCompletedOrdersAction implements Action {
    @Override
    public void execute() {
        System.out.println("Введите период дат в формате \"дд мм гггг\":");
        String dateFrom = CONSOLE_WORKER.readStringFromConsole();
        String dateTo = CONSOLE_WORKER.readStringFromConsole();
        List<Order> orders = ORDER_SERVICE.getCompletedOrder(LocalDate.parse(dateFrom, DateTimeFormatter.ofPattern("dd MM yyyy")),
                LocalDate.parse(dateTo, DateTimeFormatter.ofPattern("dd MM yyyy")));
        CONSOLE_WORKER.printList(orders);
    }
}
