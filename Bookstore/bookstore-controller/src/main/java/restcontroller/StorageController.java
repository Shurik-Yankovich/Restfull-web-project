package restcontroller;

import entity.Book;
import entity.Bookshelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.storage.StorageService;

import java.util.List;

@RestController
@RequestMapping(value = "/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping(value = "/")
    public ResponseEntity<?> create(@RequestBody Book book, @RequestBody Integer count) {
        Bookshelf bookshelf = storageService.addBookOnStorage(book, count);

        return bookshelf != null
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Bookshelf>> showList() {
        final List<Bookshelf> bookshelves = storageService.getBookshelfList();

        return bookshelves != null && !bookshelves.isEmpty()
                ? new ResponseEntity<>(bookshelves, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<List<Bookshelf>> showSortList() {
        final List<Bookshelf> bookshelves = storageService.getSortingBookshelves();

        return bookshelves != null && !bookshelves.isEmpty()
                ? new ResponseEntity<>(bookshelves, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/unsold")
    public ResponseEntity<List<Bookshelf>> showUnsoldList() {
        final List<Bookshelf> bookshelves = storageService.getUnsoldBookshelves();

        return bookshelves != null && !bookshelves.isEmpty()
                ? new ResponseEntity<>(bookshelves, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
