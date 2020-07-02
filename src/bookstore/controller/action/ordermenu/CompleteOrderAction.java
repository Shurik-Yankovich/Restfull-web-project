package bookstore.controller.action.ordermenu;

import bookstore.controller.action.Action;
import bookstore.model.Order;

import static bookstore.view.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.order.BookOrderService.ORDER_SERVICE;

public class CompleteOrderAction implements Action {
    @Override
    public void execute() {
        Order order = CONSOLE_WORKER.choiceFromList(ORDER_SERVICE.getNewOrder());
        if (ORDER_SERVICE.completeOrder(order)) {
            System.out.println("Заказ успешно завергшен!");
        } else {
            System.out.println("Неудалось завершить заказ!");
        }
    }
}
