package restcontroller;

import dto.BookDto;
import dto.BookshelfDto;
import entity.Book;
import entity.Bookshelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.storage.StorageService;
import util.converter.DtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping(value = "/")
    public ResponseEntity<?> createBookshelf(@RequestBody BookDto bookDto, @RequestBody Integer count) {
        Book book = DtoConverter.convertDtoToBook(bookDto);
        BookshelfDto bookshelfDto = DtoConverter.convertBookshelfToDto(storageService.addBookOnStorage(book, count));

        return bookshelfDto != null
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<BookshelfDto>> getBookshelfList() {
        List<Bookshelf> bookshelves = storageService.getBookshelfList();
        final List<BookshelfDto> bookshelfDtoList = bookshelves != null ?
                bookshelves.stream().map(DtoConverter::convertBookshelfToDto).collect(Collectors.toList())
                : null;

        return bookshelfDtoList != null && !bookshelfDtoList.isEmpty()
                ? new ResponseEntity<>(bookshelfDtoList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<List<BookshelfDto>> getSortBookshelfList() {
        List<Bookshelf> bookshelves = storageService.getSortingBookshelves();
        final List<BookshelfDto> bookshelfDtoList = bookshelves != null ?
                bookshelves.stream().map(DtoConverter::convertBookshelfToDto).collect(Collectors.toList())
                : null;

        return bookshelfDtoList != null && !bookshelfDtoList.isEmpty()
                ? new ResponseEntity<>(bookshelfDtoList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/unsold")
    public ResponseEntity<List<BookshelfDto>> getUnsoldBookshelfList() {
        List<Bookshelf> bookshelves = storageService.getUnsoldBookshelves();
        final List<BookshelfDto> bookshelfDtoList = bookshelves != null ?
                bookshelves.stream().map(DtoConverter::convertBookshelfToDto).collect(Collectors.toList())
                : null;

        return bookshelfDtoList != null && !bookshelfDtoList.isEmpty()
                ? new ResponseEntity<>(bookshelfDtoList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
