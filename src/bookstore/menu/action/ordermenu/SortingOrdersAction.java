package bookstore.menu.action.ordermenu;

import bookstore.menu.action.Action;

import static bookstore.menu.Console.console;
import static bookstore.service.store.ElectronicBookstore.bookstore;

public class SortingOrdersAction implements Action {
    @Override
    public void execute() {
        console.printList(bookstore.getSortingOrderList());
    }
}
