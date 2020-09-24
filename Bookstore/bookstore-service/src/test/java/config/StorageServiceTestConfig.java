package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.base.StorageRepository;
import repository.file.FileStorageRepository;
import service.request.RequestService;
import service.storage.BookStorageService;
import service.storage.StorageService;
import util.csv.StorageCsv;

import static org.mockito.Mockito.mock;

@Configuration
public class StorageServiceTestConfig {

    @Bean
    public StorageService storageService(){
        return new BookStorageService();
    }

    @Bean
    public RequestService requestService(){
        return mock(RequestService.class);
    }

    @Bean
    public StorageRepository storageRepository(){
        return mock(StorageRepository.class);
    }

    @Bean
    public FileStorageRepository fileStorageRepository(){
        return mock(FileStorageRepository.class);
    }

    @Bean
    public StorageCsv storageCsv() {
        return mock(StorageCsv.class);
    }
}
