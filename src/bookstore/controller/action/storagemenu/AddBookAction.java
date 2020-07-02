package bookstore.controller.action.storagemenu;

import bookstore.controller.action.Action;
import bookstore.model.book.Book;

import static bookstore.view.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.storage.BookStorageService.STORAGE_SERVICE;

public class AddBookAction implements Action {
    @Override
    public void execute() {
        Book book = CONSOLE_WORKER.choiceFromList(STORAGE_SERVICE.getBookshelfList()).getBook();
        System.out.println("Введите количество книг:");
        int count = CONSOLE_WORKER.readIntFromConsole();
        STORAGE_SERVICE.addBookOnStorage(book, count);
    }
}
