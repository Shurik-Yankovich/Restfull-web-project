import controller.MenuController;
import logger.LoggerApp;
import repository.base.OrderRepository;
import repository.base.RequestRepository;
import repository.base.StorageRepository;
import repository.db.DBOrderRepository;
import repository.db.DBRequestRepository;
import repository.db.DBStorageRepository;
import com.application.Application;
import com.application.ApplicationContext;
import repository.hibernate.HibernateOrderRepository;
import repository.hibernate.HibernateRequestRepository;
import repository.hibernate.HibernateStorageRepository;
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

    private static final LoggerApp logger = new LoggerApp(BookstoreMain.class);

    public static void main(String[] args) {
        logger.infoLogger("Старт программы");
        Map<Class, Class> cache = new HashMap<>();
        cache.put(OrderRepository.class, HibernateOrderRepository.class);
        cache.put(RequestRepository.class, HibernateRequestRepository.class);
        cache.put(StorageRepository.class, HibernateStorageRepository.class);
        cache.put(ViewIn.class, StoreViewIn.class);
        cache.put(ViewOut.class, StoreViewOut.class);
        cache.put(OrderService.class, BookOrderService.class);
        cache.put(RequestService.class, BookRequestService.class);
        cache.put(StorageService.class, BookStorageService.class);
//        cache.put(ConnectionUtils.class, MySqlConnectionUtils.class);
        cache.put(ISerializationService.class, SerializationService.class);
        logger.infoLogger("Инициализация контекста");
        ApplicationContext context = Application.run("org.bookstore", cache);
        logger.infoLogger("Инициализация всех классов группы \"org.bookstore\"");
        MenuController menuController = context.getObject(MenuController.class);
        logger.infoLogger("Запуск меню");
        menuController.run();
    }

}