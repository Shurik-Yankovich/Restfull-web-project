package bookstore;

import bookstore.controller.MenuController;
import bookstore.entity.Bookshelf;
import bookstore.entity.Order;
import bookstore.entity.Request;
import bookstore.repository.base.OrderRepository;
import bookstore.repository.base.RequestRepository;
import bookstore.repository.base.StorageRepository;
import bookstore.repository.list.BookOrderRepository;
import bookstore.repository.list.BookRequestRepository;
import bookstore.repository.list.BookStorageRepository;
import bookstore.util.serialize.ISerializationService;
import bookstore.util.serialize.SerializationService;
import com.application.Application;
import com.application.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static bookstore.constant.FileName.*;

public class BookstoreMain {

    public static void main(String[] args) {
//        StoreViewIn viewIn = new StoreViewIn();
//        StoreViewOut viewOut = new StoreViewOut();
//        OrderCsv orderCsv = new OrderCsv();
//        RequestCsv requestCsv = new RequestCsv();
//        StorageCsv storageCsv = new StorageCsv();
//        FileOrderRepository fileOrderRepository = new FileOrderRepository(orderCsv);
//        FileRequestRepository fileRequestRepository = new FileRequestRepository(requestCsv);
//        FileStorageRepository fileStorageRepository = new FileStorageRepository(storageCsv);
        String configFileName = "src/resources/config.properties";
        ISerializationService<Request> requestSerialize = new SerializationService<>();
        ISerializationService<Order> orderSerialize = new SerializationService<>();
        ISerializationService<Bookshelf> storageSerialize = new SerializationService<>();
        OrderRepository orderRepository = new BookOrderRepository(orderSerialize.load(ORDER_SERIALIZATION_FILE_NAME));
        RequestRepository requestRepository = new BookRequestRepository(requestSerialize.load(REQUEST_SERIALIZATION_FILE_NAME));
        StorageRepository storageRepository = new BookStorageRepository(storageSerialize.load(STORAGE_SERIALIZATION_FILE_NAME));
//        RequestService requestService = new BookRequestService(requestRepository, fileRequestRepository);
//        StorageService storageService = new BookStorageService(storageRepository, fileStorageRepository, requestService, configFileName);
//        PropertiesAnnotationHandler.setValues(storageService);
//        OrderService orderService = new BookOrderService(storageService, requestService, orderRepository, fileOrderRepository);
//        StoreAction action = new StoreAction(orderService, requestService, storageService, viewIn, viewOut);
//        Builder builder = new Builder(action);
//        Navigator navigator = new Navigator();
//        MenuController menuController = new MenuController(builder, navigator);
        Map<Class, Class> cache = new HashMap<>();
        cache.put(OrderRepository.class, BookOrderRepository.class);
        cache.put(RequestRepository.class, BookRequestRepository.class);
        cache.put(StorageRepository.class, BookStorageRepository.class);
        ApplicationContext context = Application.run("bookstore", cache);
        MenuController menuController = context.getObject(MenuController.class);
        menuController.run();
    }

}
