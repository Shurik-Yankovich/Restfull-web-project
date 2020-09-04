package bookstore;

import bookstore.controller.MenuController;
import bookstore.repository.base.OrderRepository;
import bookstore.repository.base.RequestRepository;
import bookstore.repository.base.StorageRepository;
import bookstore.repository.db.DBOrderRepository;
import bookstore.repository.db.DBRequestRepository;
import bookstore.repository.db.DBStorageRepository;
import com.application.Application;
import com.application.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class BookstoreMain {

    public static void main(String[] args) {
        Map<Class, Class> cache = new HashMap<>();
        cache.put(OrderRepository.class, DBOrderRepository.class);
        cache.put(RequestRepository.class, DBRequestRepository.class);
        cache.put(StorageRepository.class, DBStorageRepository.class);
        ApplicationContext context = Application.run("bookstore", cache);
        MenuController menuController = context.getObject(MenuController.class);
        menuController.run();
    }

}
