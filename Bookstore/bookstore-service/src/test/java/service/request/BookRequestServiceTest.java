package service.request;

import config.TestConfig;
import entity.Book;
import entity.Request;
import entity.Status;
import exeption.RepositoryException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import repository.base.RequestRepository;
import repository.file.FileRequestRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class BookRequestServiceTest {

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private FileRequestRepository fileRequestRepository;

    private static Book book;
    private static List<Book> bookList;
    private static Request expectedRequest;

    @BeforeAll
    public static void init(){
        book = new Book("Алхимик","Пауло Коэльо",2019);
        book.setId(1);
        bookList = new ArrayList<>();
        bookList.add(book);
        expectedRequest = new Request(book);
        expectedRequest.setCount(1);
    }

    @Test
    public void addRequestForBookThatHasNoRequestsYet() throws RepositoryException {
        when(requestRepository.create(any(Request.class))).thenReturn(expectedRequest);
        Request actualRequest = requestService.addRequest(book);
        assertEquals(expectedRequest, actualRequest);
    }

    @Test
    public void addRequestWithExceptionCreateInDatabase() {
        Request actualRequest = requestService.addRequest(book);
        assertNotNull(actualRequest);
    }

    @Test
    public void addRequestListByBookWithOneBookOnList() throws RepositoryException {
        when(requestRepository.create(any(Request.class))).thenReturn(expectedRequest);
        List<Request> actualRequestList = requestService.addRequestList(bookList);
        List<Request> expectedRequestList = new ArrayList<>();
        expectedRequestList.add(expectedRequest);
        assertEquals(expectedRequestList, actualRequestList);
    }

    @Test
    public void completeRequestsByBookWhenThereIsOneRequestForThisBook() throws RepositoryException {
        List<Request> databaseRequestList = new ArrayList<>();
        databaseRequestList.add(new Request(book));
        when(requestRepository.readAll()).thenReturn(databaseRequestList);
        List<Request> actualRequestList = requestService.completeRequestsByBook(book);
        assertEquals(Status.COMPLETED, actualRequestList.get(0).getStatus());
    }

    @Test
    public void completeRequest() throws RepositoryException {
        when(requestRepository.update(any(Request.class))).thenReturn(expectedRequest);
        Request request = new Request(book);
        Request actualRequest = requestService.completeRequest(request);
        assertEquals(Status.COMPLETED, actualRequest.getStatus());
    }

    @Test
    public void completeRequestWhenRequestIsNull() {
        assertThrows(NullPointerException.class, () -> {
            requestService.completeRequest(null);
        });
    }

    @Test
    public void cancelRequest() throws RepositoryException {
        when(requestRepository.update(any(Request.class))).thenReturn(any(Request.class));
        Request actualRequest = requestService.cancelRequest(new Request(book));
        assertEquals(Status.CANCELED, actualRequest.getStatus());
    }

    @Test
    public void cancelRequestWhenRequestIsNull() {
        assertThrows(NullPointerException.class, () -> {
            requestService.cancelRequest(null);
        });
    }




}
