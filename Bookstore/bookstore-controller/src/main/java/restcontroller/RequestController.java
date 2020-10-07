package restcontroller;

import entity.Book;
import entity.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.request.RequestService;

import java.util.List;

@RestController
@RequestMapping(value = "/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping(value = "/")
    public ResponseEntity<?> create(@RequestBody Book book){
        Request request = requestService.addRequest(book);

        return request != null
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PutMapping(value = "/cancel/{id}")
//    public ResponseEntity<?> cancel(@PathVariable(name = "id") int id){
//        Request request = requestService.getRequestByNumber(id);
//        request = requestService.cancelRequest(request);
//
//        return request != null
//                ? new ResponseEntity<>(HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }

    @PutMapping(value = "/complete/{id}")
    public ResponseEntity<?> complete(@PathVariable(name = "id") int id){
        Request request = requestService.getRequestByNumber(id);
        request = requestService.completeRequest(request);

        return request != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Request>> showList(){
        final List<Request> requestList = requestService.getRequestList();

        return requestList != null && !requestList.isEmpty()
                ? new ResponseEntity<>(requestList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<List<Request>> showSortList(){
        final List<Request> requestList = requestService.getSortingRequestList();

        return requestList != null && !requestList.isEmpty()
                ? new ResponseEntity<>(requestList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/new")
    public ResponseEntity<List<Request>> showNewList(){
        final List<Request> requestList = requestService.getNewRequests();

        return requestList != null && !requestList.isEmpty()
                ? new ResponseEntity<>(requestList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
