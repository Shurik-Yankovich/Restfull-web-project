package bookstore.menu.action.ordermenu;

import bookstore.menu.action.Action;
import bookstore.model.Order;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.order.BookOrderService.ORDER_SERVICE;

public class CancelOrderAction implements Action {
    @Override
    public void execute() {
        Order order = CONSOLE_WORKER.choiceFromList(ORDER_SERVICE.getOrderList());
        if (!ORDER_SERVICE.cancelOrder(order)) {
            System.out.println("Неудалось отменить заказ!");
        }
    }
}
