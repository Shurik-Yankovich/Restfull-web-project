package electronicbookstore;

import electronicbookstore.menu.controller.Controller;
import electronicbookstore.menu.controller.MenuController;

public class BookstoreMain {

    public static void main(String[] args) {
        Controller menuController = new MenuController();
        menuController.run();
    }

}
