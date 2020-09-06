import controller.MenuController;
import repository.base.OrderRepository;
import repository.base.RequestRepository;
import repository.base.StorageRepository;
import repository.db.DBOrderRepository;
import repository.db.DBRequestRepository;
import repository.db.DBStorageRepository;
import com.application.Application;
import com.application.ApplicationContext;
import service.order.BookOrderService;
import service.order.OrderService;
import service.request.BookRequestService;
import service.request.RequestService;
import service.storage.BookStorageService;
import service.storage.StorageService;
import util.connections.ConnectionUtils;
import util.connections.MySqlConnectionUtils;
import util.serialize.ISerializationService;
import util.serialize.SerializationService;
import view.in.StoreViewIn;
import view.in.ViewIn;
import view.out.StoreViewOut;
import view.out.ViewOut;

import java.util.HashMap;
import java.util.Map;

public class BookstoreMain {

    public static void main(String[] args) {
        Map<Class, Class> cache = new HashMap<>();
        cache.put(OrderRepository.class, DBOrderRepository.class);
        cache.put(RequestRepository.class, DBRequestRepository.class);
        cache.put(StorageRepository.class, DBStorageRepository.class);
        cache.put(ViewIn.class, StoreViewIn.class);
        cache.put(ViewOut.class, StoreViewOut.class);
        cache.put(OrderService.class, BookOrderService.class);
        cache.put(RequestService.class, BookRequestService.class);
        cache.put(StorageService.class, BookStorageService.class);
        cache.put(ConnectionUtils.class, MySqlConnectionUtils.class);
        cache.put(ISerializationService.class, SerializationService.class);
        ApplicationContext context = Application.run("org.bookstore", cache);
        MenuController menuController = context.getObject(MenuController.class);
        menuController.run();
    }

}