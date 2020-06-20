package bookstore.menu.action.requestmenu;

import bookstore.menu.action.Action;
import bookstore.model.book.Book;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.request.BookRequestService.REQUEST_SERVICE;
import static bookstore.service.storage.BookStorageService.STORAGE_SERVICE;

public class AddRequestAction implements Action {
    @Override
    public void execute() {
        Book book = CONSOLE_WORKER.choiceFromList(STORAGE_SERVICE.getBookshelfList()).getBook();
        REQUEST_SERVICE.addRequest(book);
    }
}
