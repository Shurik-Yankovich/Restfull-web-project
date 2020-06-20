package bookstore.menu.action.storagemenu;

import bookstore.menu.action.Action;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.storage.BookStorageService.STORAGE_SERVICE;

public class UnsortingBooksAction implements Action {
    @Override
    public void execute() {
        CONSOLE_WORKER.printList(STORAGE_SERVICE.getBookshelfList());
    }
}
