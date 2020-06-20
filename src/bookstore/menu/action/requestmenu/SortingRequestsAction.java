package bookstore.menu.action.requestmenu;

import bookstore.menu.action.Action;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.request.BookRequestService.REQUEST_SERVICE;

public class SortingRequestsAction implements Action {
    @Override
    public void execute() {
        CONSOLE_WORKER.printList(REQUEST_SERVICE.getSortingRequestList());
    }
}
