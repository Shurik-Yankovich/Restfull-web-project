package bookstore.menu.action.storagemenu;

import bookstore.menu.action.Action;
import bookstore.model.Book;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.store.BookStoreService.bookstore;

public class AddBookAction implements Action {
    @Override
    public void execute() {
        Book book = CONSOLE_WORKER.choiceFromList(bookstore.getBookList()).getBook();
        System.out.println("Введите количество книг:");
        int count = CONSOLE_WORKER.readIntFromConsole();
        bookstore.addBookOnStorage(book, count);
    }
}
