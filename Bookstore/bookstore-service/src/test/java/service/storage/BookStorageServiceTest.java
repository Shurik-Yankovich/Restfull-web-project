package service.storage;

import config.StorageServiceTestConfig;
import entity.Book;
import entity.Bookshelf;
import entity.Request;
import exeption.RepositoryException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import repository.base.StorageRepository;
import service.request.RequestService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    private static Bookshelf expectedBookshelf;
    private static List<Bookshelf> expectedBookshelfList;

    @BeforeAll
    public static void init() {
        book = new Book("Алхимик", "Пауло Коэльо", 2019);
        book.setId(1);
        expectedBookshelf = new Bookshelf(book, 7, 99.9, LocalDate.parse("2020-01-20"));
    }

    @BeforeEach
    public void initForEach() {
        expectedBookshelfList = new ArrayList<>();
        expectedBookshelfList.add(expectedBookshelf);
    }

    @Test
    public void addBookOnStorage() throws RepositoryException {
        doReturn(expectedBookshelfList).when(storageRepository).readAll();
        when(requestService.completeRequestsByBook(book)).thenReturn(new ArrayList<Request>());
        when(storageRepository.update(any(Bookshelf.class))).thenReturn(expectedBookshelf);
        Bookshelf actualBookshelf = storageService.addBookOnStorage(book, 3);
        assertEquals(expectedBookshelf.getCount(), actualBookshelf.getCount());
    }


}
