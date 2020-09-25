package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.base.RequestRepository;
import repository.file.FileRequestRepository;
import repository.hibernate.HibernateRequestRepository;
import service.request.BookRequestService;
import service.request.RequestService;
import util.csv.RequestCsv;

import static org.mockito.Mockito.mock;

@Configuration
public class RequestServiceTestConfig {

    @Bean
    public RequestService requestService() {
        return new BookRequestService();
    }

    @Bean
    public RequestRepository requestRepository() {
        return mock(HibernateRequestRepository.class);
    }

    @Bean
    public FileRequestRepository fileRequestRepository() {
        return mock(FileRequestRepository.class);
    }

    @Bean
    public RequestCsv requestCsv() {
        return mock(RequestCsv.class);
    }
}
