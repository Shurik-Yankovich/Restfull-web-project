package service.storage;

import config.StorageServiceTestConfig;
import entity.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import repository.base.StorageRepository;
import service.request.RequestService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StorageServiceTestConfig.class)
public class BookStorageServiceTest {

    @Autowired
    private StorageService storageService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private StorageRepository storageRepository;

    private static Book book;
    private static List<Book> bookList;

    @BeforeAll
    public static void init(){
        book = new Book("Алхимик", "Пауло Коэльо", 2019);
        book.setId(1);
        bookList = new ArrayList<>();
        bookList.add(book);
    }




}
