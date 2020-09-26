package service.request;

import config.RequestServiceTestConfig;
import entity.Book;
import entity.Request;
import entity.Status;
import exeption.RepositoryException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import repository.base.RequestRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RequestServiceTestConfig.class)
public class BookRequestServiceTest {

    @Autowired
    private RequestService requestService;
    @Autowired
    private RequestRepository requestRepository;

    private static Book book;
    private static List<Book> bookList;
    private static Request expectedRequest;
    private List<Request> expectedRequestList;

    @BeforeAll
    public static void init() {
        book = new Book("Алхимик", "Пауло Коэльо", 2019);
        book.setId(1);
        bookList = new ArrayList<>();
        bookList.add(book);
        expectedRequest = new Request(book);
    }

    @BeforeEach
    public void initForEach(){
        expectedRequestList = new ArrayList<>();
        expectedRequestList.add(expectedRequest);
    }

    @Test
    public void addRequestForBookThatHasNoRequestsYet() throws RepositoryException {
        doReturn(new ArrayList<Request>()).when(requestRepository).readAll();
        when(requestRepository.create(any(Request.class))).thenReturn(expectedRequest);
        Request actualRequest = requestService.addRequest(book);
        verify(requestRepository, atLeast(1)).create(any(Request.class));
        assertEquals(expectedRequest, actualRequest);
    }

    @Test
    public void addRequestWithExceptionCreateInDatabase() throws RepositoryException {
        when(requestRepository.create(any(Request.class))).thenThrow(RepositoryException.class);
        Request actualRequest = requestService.addRequest(book);
        assertNull(actualRequest);
    }

    @Test
    public void addRequestListByBookWithOneBookOnList() throws RepositoryException {
        when(requestRepository.create(any(Request.class))).thenReturn(expectedRequest);
        doReturn(new ArrayList<Request>()).when(requestRepository).readAll();
        List<Request> actualRequestList = requestService.addRequestList(bookList);
        assertEquals(expectedRequestList, actualRequestList);
    }

    @Test
    public void completeRequestsByBookWhenThereIsOneRequestForThisBook() throws RepositoryException {
//        List<Request> databaseRequestList = new ArrayList<>();
//        databaseRequestList.add(new Request(book));
//        when(requestRepository.readAll()).thenReturn(expectedRequestList);
        doReturn(expectedRequestList).when(requestRepository).readAll();
        List<Request> actualRequestList = requestService.completeRequestsByBook(book);
        assertEquals(Status.COMPLETED, actualRequestList.get(0).getStatus());
    }

    @Test
    public void completeRequestWithoutProblemUpdateInDatabase() throws RepositoryException {
        Request request = new Request(book);
        when(requestRepository.update(any(Request.class))).thenReturn(request);
        Request actualRequest = requestService.completeRequest(request);
        assertEquals(Status.COMPLETED, actualRequest.getStatus());
    }

    @Test
    public void completeRequestWhenRequestIsNull() {
        assertThrows(NullPointerException.class, () -> requestService.completeRequest(null));
    }

    @Test
    public void cancelRequestWithoutProblemUpdateInDatabase() throws RepositoryException {
        Request request = new Request(book);
        when(requestRepository.update(any(Request.class))).thenReturn(request);
        Request actualRequest = requestService.cancelRequest(request);
        assertEquals(Status.CANCELED, actualRequest.getStatus());
    }

    @Test
    public void cancelRequestWhenRequestIsNull() {
        assertThrows(NullPointerException.class, () -> requestService.cancelRequest(null));
    }

    @Test
    public void checkCompleteRequestThatHasNotBeenCompletedYet() throws RepositoryException {
        List<Integer> requestNumbers = new ArrayList<>();
        requestNumbers.add(0);
        when(requestRepository.read(anyInt())).thenReturn(expectedRequest);
        assertFalse(requestService.checkCompleteRequest(requestNumbers));
    }

    @Test
    public void checkCompleteRequestThatHasBeenCompleted() throws RepositoryException {
        List<Integer> requestNumbers = new ArrayList<>();
        requestNumbers.add(0);
        Request request = new Request(book);
        request.setStatus(Status.COMPLETED);
        when(requestRepository.read(anyInt())).thenReturn(request);
        assertTrue(requestService.checkCompleteRequest(requestNumbers));
    }

    @Test
    public void getRequestByNumberWithoutProblemReadFromDatabase() throws RepositoryException {
        when(requestRepository.read(anyInt())).thenReturn(expectedRequest);
        Request actualRequest = requestService.getRequestByNumber(0);
//        verify(requestRepository, times(1)).read(anyInt());
        assertEquals(expectedRequest, actualRequest);
    }

    @Test
    public void getRequestByNumberWithExceptionReadFromDatabase() throws RepositoryException {
        when(requestRepository.read(anyInt())).thenThrow(RepositoryException.class);
        Request actualRequest = requestService.getRequestByNumber(0);
        assertNull(actualRequest);
    }

    @Test
    public void getRequestListWithoutProblemReadFromDatabase() throws RepositoryException {
//        when(requestRepository.readAll()).thenReturn(expectedRequestList);
        doReturn(expectedRequestList).when(requestRepository).readAll();
        List<Request> actualRequestList = requestService.getRequestList();
        assertEquals(expectedRequestList, actualRequestList);
    }

    @Test
    public void getRequestListWithExceptionReadFromDatabase() throws RepositoryException {
//        when(requestRepository.readAll()).thenThrow(RepositoryException.class);
        doThrow(RepositoryException.class).when(requestRepository).readAll();
        List<Request> actualRequestList = requestService.getRequestList();
        assertNull(actualRequestList);
    }

    @Test
    public void getNewRequestsWithoutProblemReadFromDatabaseAndStatusResultRequestsWillBeNew() throws RepositoryException {
//        when(requestRepository.readAll()).thenReturn(expectedRequestList);
        doReturn(expectedRequestList).when(requestRepository).readAll();
        List<Request> actualRequestList = requestService.getNewRequests();
        assertEquals(expectedRequestList, actualRequestList);
        assertEquals(Status.NEW,actualRequestList.get(0).getStatus());
    }

    @Test
    public void getNewRequestsWithExceptionReadFromDatabase() throws RepositoryException {
//        when(requestRepository.readAll()).thenThrow(RepositoryException.class);
        doThrow(RepositoryException.class).when(requestRepository).readAll();
        List<Request> actualRequestList = requestService.getNewRequests();
        assertNull(actualRequestList);
    }

    @Test
    public void getSortingRequestListWithoutProblemReadFromDatabase() throws RepositoryException {
//        when(requestRepository.readAll()).thenReturn(expectedRequestList);
        doReturn(expectedRequestList).when(requestRepository).readAll();
        List<Request> actualRequestList = requestService.getSortingRequestList();
        assertEquals(expectedRequestList, actualRequestList);
    }

    @Test
    public void getSortingRequestListWithExceptionReadFromDatabase() throws RepositoryException {
//        when(requestRepository.readAll()).thenThrow(RepositoryException.class);
        doThrow(RepositoryException.class).when(requestRepository).readAll();
        List<Request> actualRequestList = requestService.getSortingRequestList();
        assertNull(actualRequestList);
    }




}
