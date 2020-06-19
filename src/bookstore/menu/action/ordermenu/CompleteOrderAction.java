package bookstore.menu.action.ordermenu;

import bookstore.menu.action.Action;
import bookstore.model.Order;

import static bookstore.menu.Console.console;
import static bookstore.service.store.BookStoreService.bookstore;

public class CompleteOrderAction implements Action {
    @Override
    public void execute() {
        Order order = console.choiceFromList(bookstore.getOrderList());
        if (!bookstore.completeOrder(order)) {
            System.out.println("Неудалось завершить заказ!");
        }
    }
}
