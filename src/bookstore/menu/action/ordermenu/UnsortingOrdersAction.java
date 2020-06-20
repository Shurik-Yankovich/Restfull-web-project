package bookstore.menu.action.ordermenu;

import bookstore.menu.action.Action;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.order.BookOrderService.ORDER_SERVICE;

public class UnsortingOrdersAction implements Action {
    @Override
    public void execute() {
        CONSOLE_WORKER.printList(ORDER_SERVICE.getOrderList());
    }
}
