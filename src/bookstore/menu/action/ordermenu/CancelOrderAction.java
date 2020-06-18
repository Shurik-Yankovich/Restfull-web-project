package bookstore.menu.action.ordermenu;

import bookstore.menu.action.Action;
import bookstore.model.Order;

import static bookstore.menu.Console.console;
import static bookstore.service.store.ElectronicBookstore.bookstore;

public class CancelOrderAction implements Action {
    @Override
    public void execute() {
        Order order = console.choiceFromList(bookstore.getOrderList());
        bookstore.cancelOrder(order);
    }
}
