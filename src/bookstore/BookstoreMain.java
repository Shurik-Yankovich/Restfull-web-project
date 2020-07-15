package bookstore;

import bookstore.controller.MenuController;
import bookstore.controller.action.StoreAction;
import bookstore.controller.builder.Buildable;
import bookstore.controller.builder.Builder;
import bookstore.controller.navigator.Navigable;
import bookstore.controller.navigator.Navigator;
import bookstore.entity.Bookshelf;
import bookstore.entity.Order;
import bookstore.entity.Request;
import bookstore.serialize.ISerializationService;
import bookstore.serialize.SerializationService;
import bookstore.service.order.BookOrderService;
import bookstore.service.order.OrderService;
import bookstore.service.request.BookRequestService;
import bookstore.service.request.RequestService;
import bookstore.service.storage.BookStorageService;
import bookstore.service.storage.StorageService;
import bookstore.view.in.StoreViewIn;
import bookstore.view.out.StoreViewOut;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static bookstore.constant.FileName.*;

public class BookstoreMain {

    public static void main(String[] args) {
        StoreViewIn viewIn = new StoreViewIn();
        StoreViewOut viewOut = new StoreViewOut();
        Properties property = new Properties();
        try (FileInputStream fis =  new FileInputStream("src/resources/config.properties")) {
            property.load(fis);
            ISerializationService<Request> requestSerialize = new SerializationService<>();
            ISerializationService<Order> orderSerialize = new SerializationService<>();
            ISerializationService<Bookshelf> storageSerialize = new SerializationService<>();
            RequestService requestService = new BookRequestService(requestSerialize.load(REQUEST_SERIALIZATION_FILE_NAME));
//            RequestService requestService = new BookRequestService();
            StorageService storageService = new BookStorageService(storageSerialize.load(STORAGE_SERIALIZATION_FILE_NAME),
                    requestService, property);
//            StorageService storageService = new BookStorageService(BookGenerator.generate(), requestService, property);
            OrderService orderService = new BookOrderService(storageService, requestService, orderSerialize.load(ORDER_SERIALIZATION_FILE_NAME));
//            OrderService orderService = new BookOrderService(storageService, requestService);
            StoreAction action = new StoreAction(orderService, requestService, storageService, viewIn, viewOut);
            Buildable builder = new Builder(action);
            Navigable navigator = new Navigator();
            MenuController menuController = new MenuController(builder, navigator);
            menuController.run();
        } catch (IOException e) {
            viewOut.printExceptionMessage("ОШИБКА: Файл отсуствует!\n" + e.getMessage());
        }
    }

}
