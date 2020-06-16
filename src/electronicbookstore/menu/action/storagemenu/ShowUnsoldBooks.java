package electronicbookstore.menu.action.storagemenu;

import electronicbookstore.menu.action.Action;

import static electronicbookstore.menu.Console.console;
import static electronicbookstore.service.store.ElectronicBookstore.bookstore;

public class ShowUnsoldBooks implements Action {
    @Override
    public void execute() {
        console.printList(bookstore.getUnsoldBookList());
    }
}
