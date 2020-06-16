package electronicbookstore.menu.action.requestmenu;

import electronicbookstore.menu.action.Action;

import static electronicbookstore.menu.Console.console;
import static electronicbookstore.service.store.ElectronicBookstore.bookstore;

public class UnsortingRequestAction implements Action {
    @Override
    public void execute() {
        console.printList(bookstore.getRequestList());
    }
}
