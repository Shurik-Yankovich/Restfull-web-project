package config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.base.OrderRepository;
import repository.base.RequestRepository;
import repository.base.StorageRepository;
import repository.file.FileOrderRepository;
import repository.file.FileRequestRepository;
import repository.file.FileStorageRepository;
import repository.hibernate.HibernateOrderRepository;
import repository.hibernate.HibernateRequestRepository;
import repository.hibernate.HibernateStorageRepository;
import service.order.BookOrderService;
import service.order.OrderService;
import service.request.BookRequestService;
import service.request.RequestService;
import service.storage.BookStorageService;
import service.storage.StorageService;
import util.csv.OrderCsv;
import util.csv.RequestCsv;
import util.csv.StorageCsv;

@Configuration
public class BookstoreConfig {

    @Bean
    public OrderService orderService() {
        return new BookOrderService();
    }

    @Bean
    public RequestService requestService() {
        return new BookRequestService();
    }

    @Bean
    public StorageService storageService() {
        return new BookStorageService();
    }

    @Bean
    public OrderRepository orderRepository() {
        return new HibernateOrderRepository();
    }

    @Bean
    public RequestRepository requestRepository() {
        return new HibernateRequestRepository();
    }

    @Bean
    public StorageRepository storageRepository() {
        return new HibernateStorageRepository();
    }

    @Bean
    public FileOrderRepository fileOrderRepository() {
        return new FileOrderRepository();
    }

    @Bean
    public FileRequestRepository fileRequestRepository() {
        return new FileRequestRepository();
    }

    @Bean
    public FileStorageRepository fileStorageRepository() {
        return new FileStorageRepository();
    }

    @Bean
    public OrderCsv orderCsv() {
        return new OrderCsv();
    }

    @Bean
    public RequestCsv requestCsv() {
        return new RequestCsv();
    }

    @Bean
    public StorageCsv storageCsv() {
        return new StorageCsv();
    }

}
