package bookstore;

import bookstore.controller.MenuController;
import bookstore.controller.action.StoreAction;
import bookstore.controller.builder.Buildable;
import bookstore.controller.builder.Builder;
import bookstore.controller.navigator.Navigable;
import bookstore.controller.navigator.Navigator;
import bookstore.exeption.RepositoryException;
import bookstore.service.order.BookOrderService;
import bookstore.service.order.OrderService;
import bookstore.service.request.BookRequestService;
import bookstore.service.request.RequestService;
import bookstore.service.storage.BookStorageService;
import bookstore.service.storage.StorageService;
import bookstore.util.BookGenerator;
import bookstore.view.in.StoreViewIn;
import bookstore.view.out.StoreViewOut;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BookstoreMain {

    public static void main(String[] args) {
        StoreViewIn viewIn = new StoreViewIn();
        StoreViewOut viewOut = new StoreViewOut();
        Properties property = new Properties();
        try (FileInputStream fis =  new FileInputStream("src/resources/config.properties")) {
            property.load(fis);
            RequestService requestService = new BookRequestService();
            StorageService storageService = new BookStorageService(BookGenerator.generate(), requestService, property);
            OrderService orderService = new BookOrderService(storageService, requestService);
            StoreAction action = new StoreAction(orderService, requestService, storageService, viewIn, viewOut);
            Buildable builder = new Builder(action);
            Navigable navigator = new Navigator();
            MenuController menuController = new MenuController(builder, navigator);
            menuController.run();
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        } catch (IOException e) {
            viewOut.printExceptionMessage("ОШИБКА: Файл свойств отсуствует!");
        }
    }

}
