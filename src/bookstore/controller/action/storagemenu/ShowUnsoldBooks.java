package bookstore.controller.action.storagemenu;

import bookstore.controller.action.Action;

import static bookstore.view.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.storage.BookStorageService.STORAGE_SERVICE;

public class ShowUnsoldBooks implements Action {
    @Override
    public void execute() {
        CONSOLE_WORKER.printList(STORAGE_SERVICE.getUnsoldBookshelves());
    }
}
