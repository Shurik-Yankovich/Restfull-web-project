package bookstore.controller.action.ordermenu;

import bookstore.controller.action.Action;
import bookstore.model.Order;

import java.time.LocalDate;
import java.util.List;

import static bookstore.view.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.order.BookOrderService.ORDER_SERVICE;

public class ShowCompletedOrdersAction implements Action {
    @Override
    public void execute() {
        System.out.println("Введите период дат в формате \"дд мм гггг\":");
        LocalDate dateFrom = CONSOLE_WORKER.getDateFromConsole();
        LocalDate dateTo = CONSOLE_WORKER.getDateFromConsole();
        List<Order> orders = ORDER_SERVICE.getCompletedOrder(dateFrom, dateTo);
        CONSOLE_WORKER.printList(orders);
    }
}
