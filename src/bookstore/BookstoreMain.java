package bookstore;

import bookstore.controller.Builder;
import bookstore.controller.MenuController;
import bookstore.controller.Navigator;
import bookstore.controller.action.StoreAction;
import bookstore.entity.Bookshelf;
import bookstore.entity.Order;
import bookstore.entity.Request;
import bookstore.repository.base.OrderRepository;
import bookstore.repository.base.RequestRepository;
import bookstore.repository.base.StorageRepository;
import bookstore.repository.file.FileOrderRepository;
import bookstore.repository.file.FileRequestRepository;
import bookstore.repository.file.FileStorageRepository;
import bookstore.repository.list.BookOrderRepository;
import bookstore.repository.list.BookRequestRepository;
import bookstore.repository.list.BookStorageRepository;
import bookstore.service.order.BookOrderService;
import bookstore.service.order.OrderService;
import bookstore.service.request.BookRequestService;
import bookstore.service.request.RequestService;
import bookstore.service.storage.BookStorageService;
import bookstore.service.storage.StorageService;
import bookstore.util.csv.OrderCsv;
import bookstore.util.csv.RequestCsv;
import bookstore.util.csv.StorageCsv;
import bookstore.util.serialize.ISerializationService;
import bookstore.util.serialize.SerializationService;
import bookstore.view.in.StoreViewIn;
import bookstore.view.out.StoreViewOut;
import com.handler.PropertiesAnnotationHandler;

import static bookstore.constant.FileName.*;

public class BookstoreMain {

    public static void main(String[] args) {
        StoreViewIn viewIn = new StoreViewIn();
        StoreViewOut viewOut = new StoreViewOut();
        OrderCsv orderCsv = new OrderCsv();
        RequestCsv requestCsv = new RequestCsv();
        StorageCsv storageCsv = new StorageCsv();
        FileOrderRepository fileOrderRepository = new FileOrderRepository(orderCsv);
        FileRequestRepository fileRequestRepository = new FileRequestRepository(requestCsv);
        FileStorageRepository fileStorageRepository = new FileStorageRepository(storageCsv);
        String configFileName = "src/resources/config.properties";
        ISerializationService<Request> requestSerialize = new SerializationService<>();
        ISerializationService<Order> orderSerialize = new SerializationService<>();
        ISerializationService<Bookshelf> storageSerialize = new SerializationService<>();
        OrderRepository orderRepository = new BookOrderRepository(orderSerialize.load(ORDER_SERIALIZATION_FILE_NAME));
        RequestRepository requestRepository = new BookRequestRepository(requestSerialize.load(REQUEST_SERIALIZATION_FILE_NAME));
        StorageRepository storageRepository = new BookStorageRepository(storageSerialize.load(STORAGE_SERIALIZATION_FILE_NAME));
        RequestService requestService = new BookRequestService(requestRepository, fileRequestRepository);
        StorageService storageService = new BookStorageService(storageRepository, fileStorageRepository, requestService, configFileName);
        PropertiesAnnotationHandler.setValues(storageService);
        OrderService orderService = new BookOrderService(storageService, requestService, orderRepository, fileOrderRepository);
        StoreAction action = new StoreAction(orderService, requestService, storageService, viewIn, viewOut);
        Builder builder = new Builder(action);
        Navigator navigator = new Navigator();
        MenuController menuController = new MenuController(builder, navigator);
        menuController.run();
    }

}
