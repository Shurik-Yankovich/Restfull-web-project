package electronicbookstore.menu.action.requestmenu;

import electronicbookstore.menu.action.Action;
import electronicbookstore.model.Book;

import static electronicbookstore.menu.Console.console;
import static electronicbookstore.service.store.ElectronicBookstore.bookstore;

public class AddRequestAction implements Action {
    @Override
    public void execute() {
        Book book = console.choiceFromList(bookstore.getBookList()).getBook();
        bookstore.addRequest(book);
    }
}
