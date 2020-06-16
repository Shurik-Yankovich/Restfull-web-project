package electronicbookstore.menu.action.ordermenu;

import electronicbookstore.menu.action.Action;
import electronicbookstore.model.Order;

import static electronicbookstore.menu.Console.console;
import static electronicbookstore.service.store.ElectronicBookstore.bookstore;

public class CancelOrderAction implements Action {
    @Override
    public void execute() {
        Order order = console.choiceFromList(bookstore.getOrderList());
        bookstore.cancelOrder(order);
    }
}
