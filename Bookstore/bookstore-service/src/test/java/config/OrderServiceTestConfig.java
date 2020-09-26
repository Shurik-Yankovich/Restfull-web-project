package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.base.OrderRepository;
import repository.file.FileOrderRepository;
import service.order.BookOrderService;
import service.order.OrderService;
import service.request.RequestService;
import service.storage.StorageService;
import util.csv.OrderCsv;

import static org.mockito.Mockito.mock;

@Configuration
public class OrderServiceTestConfig {

    @Bean
    public OrderService orderService() {
        return new BookOrderService();
    }

    @Bean
    public RequestService requestService() {
        return mock(RequestService.class);
    }

    @Bean
    public StorageService storageService() {
        return mock(StorageService.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return mock(OrderRepository.class);
    }

    @Bean
    public FileOrderRepository fileOrderRepository() {
        return mock(FileOrderRepository.class);
    }

    @Bean
    public OrderCsv orderCsv() {
        return mock(OrderCsv.class);
    }
}
