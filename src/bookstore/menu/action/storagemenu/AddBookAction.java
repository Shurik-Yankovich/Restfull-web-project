package bookstore.menu.action.storagemenu;

import bookstore.menu.action.Action;
import bookstore.model.Book;

import static bookstore.menu.Console.console;
import static bookstore.service.store.ElectronicBookstore.bookstore;

public class AddBookAction implements Action {
    @Override
    public void execute() {
        Book book = console.choiceFromList(bookstore.getBookList()).getBook();
        bookstore.addBookOnStorage(book);
    }
}
