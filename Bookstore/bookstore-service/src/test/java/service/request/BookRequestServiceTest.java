package service.request;

import config.TestConfig;
import entity.Book;
import entity.Request;
import exeption.RepositoryException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import repository.base.RequestRepository;
import repository.file.FileRequestRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private static final Book book = mock(Book.class);

    @Test
    public void addRequest() throws RepositoryException {
//        when(requestService.)
        Request request = new Request(book);
        request.setCount(1);
        when(requestRepository.create(any(Request.class))).thenReturn(request);
        Request request1 = requestService.addRequest(book);
        assertEquals(request1, request);
    }

}
