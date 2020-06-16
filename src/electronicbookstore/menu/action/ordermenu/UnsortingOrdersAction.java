package electronicbookstore.menu.action.ordermenu;

import electronicbookstore.menu.action.Action;

import static electronicbookstore.menu.Console.console;
import static electronicbookstore.service.store.ElectronicBookstore.bookstore;

public class UnsortingOrdersAction implements Action {
    @Override
    public void execute() {
        console.printList(bookstore.getOrderList());
    }
}
