package bookstore.menu.action.requestmenu;

import bookstore.menu.action.Action;
import bookstore.model.Book;

import static bookstore.menu.Console.console;
import static bookstore.service.store.BookStoreService.bookstore;

public class AddRequestAction implements Action {
    @Override
    public void execute() {
        Book book = console.choiceFromList(bookstore.getBookList()).getBook();
        bookstore.addRequest(book);
    }
}
