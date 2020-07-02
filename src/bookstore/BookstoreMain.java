package bookstore;

import bookstore.controller.Action;
import bookstore.controller.MenuController;
import bookstore.controller.builder.Buildable;
import bookstore.controller.builder.Builder;
import bookstore.controller.navigator.Navigable;
import bookstore.controller.navigator.Navigator;
import bookstore.service.order.BookOrderService;
import bookstore.service.order.OrderService;
import bookstore.service.request.BookRequestService;
import bookstore.service.request.RequestService;
import bookstore.service.storage.BookStorageService;
import bookstore.service.storage.StorageService;
import bookstore.util.BookGenerator;
import bookstore.view.ViewIn;
import bookstore.view.ViewOut;

public class BookstoreMain {

    public static void main(String[] args) {
        ViewIn viewIn = new ViewIn();
        ViewOut viewOut = new ViewOut();
        OrderService orderService = new BookOrderService();
        RequestService requestService = new BookRequestService();
        StorageService storageService = new BookStorageService(BookGenerator.generate());
        Action action = new Action(orderService, requestService, storageService, viewIn, viewOut);
        Buildable builder = new Builder();
        Navigable navigator = new Navigator();
        MenuController menuController = new MenuController(builder, navigator);
        menuController.run();
    }

}
