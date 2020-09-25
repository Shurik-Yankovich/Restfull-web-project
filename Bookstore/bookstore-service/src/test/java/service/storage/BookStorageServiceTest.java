package service.storage;

import config.StorageServiceTestConfig;
import entity.*;
import exeption.RepositoryException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import repository.base.StorageRepository;
import service.request.RequestService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    }

    @BeforeEach
    public void initForEach() {
        expectedBookshelf = new Bookshelf(book, 7, 99.9, LocalDate.parse("2020-01-20"));
        expectedBookshelfList = new ArrayList<>();
        expectedBookshelfList.add(expectedBookshelf);
    }

    @Test
    public void addBookOnStorageWithTwoParametersWithoutAnyProblemsWhenThisBookIsInDatabase() throws RepositoryException {
        doReturn(expectedBookshelf).when(storageRepository).read(anyInt());
        when(requestService.completeRequestsByBook(book)).thenReturn(new ArrayList<Request>());
        when(storageRepository.update(any(Bookshelf.class))).thenReturn(expectedBookshelf);
        Bookshelf actualBookshelf = storageService.addBookOnStorage(book, 3);
        assertEquals(expectedBookshelf.getCount(), actualBookshelf.getCount());
    }

    @Test
    public void addBookOnStorageWithTwoParametersWithExceptionInDatabaseAccessWhenThisBookIsInDatabase() throws RepositoryException {
        when(storageRepository.readAll()).thenThrow(RepositoryException.class);
        Bookshelf actualBookshelf = storageService.addBookOnStorage(book, 3);
        assertNull(actualBookshelf);
    }

    @Test
    public void addBookOnStorageWithThreeParametersWhenThisBookIsNotInDatabase() throws RepositoryException {
        when(storageRepository.create(any(Bookshelf.class))).thenReturn(expectedBookshelf);
        Bookshelf actualBookshelf = storageService.addBookOnStorage(book, 7, 99.9);
        assertEquals(expectedBookshelf, actualBookshelf);
    }

    @Test
    public void getTotalPriceForTwoBooksWithoutAnyExceptions() throws RepositoryException {
        Bookshelf bookshelf = new Bookshelf(mock(Book.class), 1, 0.1, LocalDate.parse("2020-01-20"));
//        when(storageRepository.read(anyInt())).thenReturn(expectedBookshelf).thenReturn(bookshelf);
        doReturn(expectedBookshelf, bookshelf).when(storageRepository).read(anyInt());
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        bookList.add(mock(Book.class));
        Double actualPrice = storageService.getTotalPrice(bookList);
        assertEquals(expectedBookshelf.getPrice() + bookshelf.getPrice(), actualPrice);
    }

    @Test
    public void getTotalPriceForOneBookWithExceptionsReadFromDatabase() throws RepositoryException {
        when(storageRepository.read(anyInt())).thenThrow(RepositoryException.class);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        bookList.add(mock(Book.class));
        assertThrows(RepositoryException.class, () -> {
            storageService.getTotalPrice(bookList);
        });
    }

    @Test
    public void checkBooksNotInStorageWhenCountOfBookIsZero() throws RepositoryException {
        expectedBookshelf.setCount(0);
//        when(storageRepository.read(anyInt())).thenReturn(expectedBookshelf);
        doReturn(expectedBookshelf).when(storageRepository).read(anyInt());
        List<Book> expectedBookList = new ArrayList<>();
        expectedBookList.add(book);
        List<Book> actualBookList = storageService.checkBooksNotInStorage(expectedBookList);
        verify(storageRepository, never()).update(any(Bookshelf.class));
        assertEquals(expectedBookList, actualBookList);
    }

    @Test
    public void checkBooksNotInStorageWhenCountOfBooksMoreThanZero() throws RepositoryException {
//        when(storageRepository.read(anyInt())).thenReturn(expectedBookshelf);
        doReturn(expectedBookshelf).when(storageRepository).read(anyInt());
        when(storageRepository.update(any(Bookshelf.class))).thenReturn(expectedBookshelf);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        List<Book> actualBookList = storageService.checkBooksNotInStorage(bookList);
        assertEquals(new ArrayList<Book>(), actualBookList);
    }

    @Test
    public void cancelBookReservationWithoutAnyRequestForOrder() throws RepositoryException {
        Order order = mock(Order.class);
        when(order.getRequests()).thenReturn(null);
        storageService.cancelBookReservation(order);
        verify(storageRepository, never()).update(any(Bookshelf.class));
    }

    @Test
    public void cancelBookReservation1() throws RepositoryException {
        Order order = mock(Order.class);
        Request request = new Request(book);
        request.setStatus(Status.COMPLETED);
        List<Request> requestList = new ArrayList<>();
        requestList.add(request);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        when(order.getRequests()).thenReturn(requestList);
        when(order.getBooks()).thenReturn(bookList);
        doReturn(expectedBookshelf).when(storageRepository).read(anyInt());
        doReturn(expectedBookshelf).when(storageRepository).update(any(Bookshelf.class));
        storageService.cancelBookReservation(order);
        verify(storageRepository, times(1)).update(any(Bookshelf.class));
        verify(storageRepository, times(1)).read(anyInt());
    }

    @Test
    public void cancelBookReservationWithExceptionReadFromDatabase() throws RepositoryException {
        Order order = mock(Order.class);
        Request request = new Request(book);
        request.setStatus(Status.COMPLETED);
        List<Request> requestList = new ArrayList<>();
        requestList.add(request);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        when(order.getRequests()).thenReturn(requestList);
        when(order.getBooks()).thenReturn(bookList);
        doThrow(RepositoryException.class).when(storageRepository).read(anyInt());
        assertThrows(RepositoryException.class, () -> {
            storageService.cancelBookReservation(order);
        });
    }

    @Test
    public void getBookshelfByBookWithoutProblemReadFromDatabase() throws RepositoryException {
        doReturn(expectedBookshelf).when(storageRepository).read(anyInt());
        Bookshelf actualBookshelf = storageService.getBookshelf(book);
        assertEquals(expectedBookshelf, actualBookshelf);
    }

    @Test
    public void getBookshelfByBookWithExceptionReadFromDatabase() throws RepositoryException {
        doThrow(RepositoryException.class).when(storageRepository).read(anyInt());
        Bookshelf actualBookshelf = storageService.getBookshelf(book);
        assertNull(actualBookshelf);
    }

    @Test
    public void getBookshelfListWithoutProblemReadFromDatabase() throws RepositoryException {
        doReturn(expectedBookshelfList).when(storageRepository).readAll();
        List<Bookshelf> actualBookshelfList = storageService.getBookshelfList();
        assertEquals(expectedBookshelfList, actualBookshelfList);
    }

    @Test
    public void getBookshelfListWithExceptionReadFromDatabase() throws RepositoryException {
        doThrow(RepositoryException.class).when(storageRepository).readAll();
        List<Bookshelf> actualBookshelfList = storageService.getBookshelfList();
        assertNull(actualBookshelfList);
    }

    @Test
    public void getSortingBookshelvesWithoutProblemReadFromDatabase() throws RepositoryException {
        doReturn(expectedBookshelfList).when(storageRepository).readAll();
        List<Bookshelf> actualBookshelfList = storageService.getSortingBookshelves();
        assertEquals(expectedBookshelfList, actualBookshelfList);
    }

    @Test
    public void getSortingBookshelvesWithExceptionReadFromDatabase() throws RepositoryException {
        doThrow(RepositoryException.class).when(storageRepository).readAll();
        List<Bookshelf> actualBookshelfList = storageService.getSortingBookshelves();
        assertNull(actualBookshelfList);
    }

    @Test
    public void getUnsoldBookshelvesWithoutProblemReadFromDatabase() throws RepositoryException {
        doReturn(expectedBookshelfList).when(storageRepository).readAll();
        List<Bookshelf> actualBookshelfList = storageService.getUnsoldBookshelves();
        assertEquals(expectedBookshelfList, actualBookshelfList);
    }

    @Test
    public void getUnsoldBookshelvesWithExceptionReadFromDatabase() throws RepositoryException {
        doThrow(RepositoryException.class).when(storageRepository).readAll();
        List<Bookshelf> actualBookshelfList = storageService.getUnsoldBookshelves();
        assertNull(actualBookshelfList);
    }



}
