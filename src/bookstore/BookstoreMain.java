package bookstore;

import bookstore.controller.Builder;
import bookstore.controller.MenuController;
import bookstore.controller.Navigator;
import bookstore.controller.action.StoreAction;
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

import static bookstore.constant.FileName.*;

public class BookstoreMain {

    public static void main(String[] args) {
        StoreViewIn viewIn = new StoreViewIn();
        StoreViewOut viewOut = new StoreViewOut();
        String configFileName = "src/resources/config.properties";
        ISerializationService<Request> requestSerialize = new SerializationService<>();
        ISerializationService<Order> orderSerialize = new SerializationService<>();
        ISerializationService<Bookshelf> storageSerialize = new SerializationService<>();
        RequestService requestService = new BookRequestService(requestSerialize.load(REQUEST_SERIALIZATION_FILE_NAME));
        StorageService storageService = new BookStorageService(storageSerialize.load(STORAGE_SERIALIZATION_FILE_NAME),
                requestService, configFileName);
        OrderService orderService = new BookOrderService(storageService, requestService, orderSerialize.load(ORDER_SERIALIZATION_FILE_NAME));
        StoreAction action = new StoreAction(orderService, requestService, storageService, viewIn, viewOut);
        Builder builder = new Builder(action);
        Navigator navigator = new Navigator();
        MenuController menuController = new MenuController(builder, navigator);
        menuController.run();
    }

}
