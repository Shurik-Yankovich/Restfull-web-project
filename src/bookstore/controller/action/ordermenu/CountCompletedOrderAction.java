package bookstore.controller.action.ordermenu;

import bookstore.controller.action.Action;

import java.time.LocalDate;

import static bookstore.view.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.order.BookOrderService.ORDER_SERVICE;

public class CountCompletedOrderAction implements Action {
    @Override
    public void execute() {
        System.out.println("Введите период дат в формате \"дд мм гггг\":");
        LocalDate dateFrom = CONSOLE_WORKER.getDateFromConsole();
        LocalDate dateTo = CONSOLE_WORKER.getDateFromConsole();
        int numberCompletedOrder = ORDER_SERVICE.getCountCompletedOrder(dateFrom, dateTo);
        System.out.println("За данный промежуток времени завершено " + numberCompletedOrder + " заказов");
    }
}
