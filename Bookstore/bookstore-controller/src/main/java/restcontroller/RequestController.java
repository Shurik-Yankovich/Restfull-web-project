package restcontroller;

import dto.BookDto;
import dto.RequestDto;
import entity.Book;
import entity.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.request.RequestService;
import util.converter.DtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping(value = "/")
    public ResponseEntity<?> createRequest(@RequestBody BookDto bookDto) {
        Book book = DtoConverter.convertDtoToBook(bookDto);
        RequestDto requestDto = DtoConverter.convertRequestToDto(requestService.addRequest(book));

        return requestDto != null
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PutMapping(value = "/{id}/cancel")
//    public ResponseEntity<?> cancelRequest(@PathVariable(name = "id") int id){
//        Request request = requestService.getRequestByNumber(id);
//        RequestDto requestDto = DtoConverter.convertRequestToDto(requestService.cancelRequest(request));
//
//        return requestDto != null
//                ? new ResponseEntity<>(HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }

    @PutMapping(value = "/{id}/complete")
    public ResponseEntity<?> completeRequest(@PathVariable(name = "id") int id) {
        Request request = requestService.getRequestByNumber(id);
        RequestDto requestDto = DtoConverter.convertRequestToDto(requestService.completeRequest(request));

        return requestDto != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<RequestDto>> getRequestList() {
        List<Request> requests = requestService.getRequestList();
        final List<RequestDto> requestDtoList = requests != null ?
                requests.stream().map(DtoConverter::convertRequestToDto).collect(Collectors.toList())
                : null;

        return requestDtoList != null && !requestDtoList.isEmpty()
                ? new ResponseEntity<>(requestDtoList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<List<RequestDto>> getSortRequestList() {
        List<Request> requests = requestService.getSortingRequestList();
        final List<RequestDto> requestList = requests != null ?
                requests.stream().map(DtoConverter::convertRequestToDto).collect(Collectors.toList())
                : null;

        return requestList != null && !requestList.isEmpty()
                ? new ResponseEntity<>(requestList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/new")
    public ResponseEntity<List<RequestDto>> getRequestsWithStatusNew() {
        List<Request> requests = requestService.getNewRequests();
        final List<RequestDto> requestList = requests != null ?
                requests.stream().map(DtoConverter::convertRequestToDto).collect(Collectors.toList())
                : null;

        return requestList != null && !requestList.isEmpty()
                ? new ResponseEntity<>(requestList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
