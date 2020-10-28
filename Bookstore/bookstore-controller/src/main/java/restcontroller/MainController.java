package restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import service.storage.StorageService;

@Controller
public class MainController {

    @Autowired
    private StorageService storageService;

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }
}
