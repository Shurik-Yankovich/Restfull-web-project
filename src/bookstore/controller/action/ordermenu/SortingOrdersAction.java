package bookstore.controller.action.ordermenu;

import bookstore.controller.action.Action;

import static bookstore.view.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.order.BookOrderService.ORDER_SERVICE;

public class SortingOrdersAction implements Action {
    @Override
    public void execute() {
        CONSOLE_WORKER.printList(ORDER_SERVICE.getSortingOrderList());
    }
}
