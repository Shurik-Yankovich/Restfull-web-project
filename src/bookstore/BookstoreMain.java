package bookstore;

import bookstore.menu.controller.Controller;
import bookstore.menu.controller.MenuController;

public class BookstoreMain {

    public static void main(String[] args) {
        Controller menuController = new MenuController();
        menuController.run();
    }

}
