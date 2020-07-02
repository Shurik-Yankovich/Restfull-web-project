package bookstore.controller.action.requestmenu;

import bookstore.controller.action.Action;

import static bookstore.view.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.request.BookRequestService.REQUEST_SERVICE;

public class SortingRequestsAction implements Action {
    @Override
    public void execute() {
        CONSOLE_WORKER.printList(REQUEST_SERVICE.getSortingRequestList());
    }
}
