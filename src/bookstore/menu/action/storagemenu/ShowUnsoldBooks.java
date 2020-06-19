package bookstore.menu.action.storagemenu;

import bookstore.menu.action.Action;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.store.BookStoreService.bookstore;

public class ShowUnsoldBooks implements Action {
    @Override
    public void execute() {
        CONSOLE_WORKER.printList(bookstore.getUnsoldBookList());
    }
}
