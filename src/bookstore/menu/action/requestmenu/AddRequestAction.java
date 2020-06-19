package bookstore.menu.action.requestmenu;

import bookstore.menu.action.Action;
import bookstore.model.book.Book;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.store.BookStoreService.bookstore;

public class AddRequestAction implements Action {
    @Override
    public void execute() {
        Book book = CONSOLE_WORKER.choiceFromList(bookstore.getBookList()).getBook();
        bookstore.addRequest(book);
    }
}
