package restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import service.storage.StorageService;

@Controller
public class MainController {

    @Autowired
    private StorageService storageService;

    @GetMapping(value = "/")
//    @PreAuthorize("hasRole('ROLE_')")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/storage/show")
    public String showStorage(Model model) {
        model.addAttribute("bookshelves", storageService.getBookshelfList());
        return "storage";
    }

//    @GetMapping(value = "/error")
//    public String error() {
//        return "error";
//    }
}
