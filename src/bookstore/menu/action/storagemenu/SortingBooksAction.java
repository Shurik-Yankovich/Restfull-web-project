package bookstore.menu.action.storagemenu;

import bookstore.menu.action.Action;

import static bookstore.menu.Console.console;
import static bookstore.service.store.BookStoreService.bookstore;

public class SortingBooksAction implements Action {
    @Override
    public void execute() {
        console.printList(bookstore.getSortingBookList());
    }
}
