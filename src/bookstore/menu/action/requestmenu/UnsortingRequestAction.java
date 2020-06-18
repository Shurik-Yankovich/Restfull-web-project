package bookstore.menu.action.requestmenu;

import bookstore.menu.action.Action;

import static bookstore.menu.Console.console;
import static bookstore.service.store.ElectronicBookstore.bookstore;

public class UnsortingRequestAction implements Action {
    @Override
    public void execute() {
        console.printList(bookstore.getRequestList());
    }
}
