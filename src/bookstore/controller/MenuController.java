package bookstore.controller;

import bookstore.controller.builder.Buildable;
import bookstore.controller.model.Menu;
import bookstore.controller.navigator.Navigable;


public class MenuController {

    private Buildable builder;
    private Navigable navigator;

    public MenuController(Buildable builder, Navigable navigator) {
        this.builder = builder;
        this.navigator = navigator;
    }

    public void run() {
        int choice;
        Menu currentMenu;

        builder.buildMenu();
        navigator.setCurrentMenu(builder.getRootMenu());
        while (true) {
            navigator.printMenu();
            choice = builder.getStoreAction().readIntFromConsole();
            currentMenu = navigator.getCurrentMenu();
            if (choice >= 0 && choice < currentMenu.getMenuItems().length) {
                navigator.navigate(choice);
                if (currentMenu.getName().equals(navigator.getCurrentMenu().getName())) {
                    navigator.setCurrentMenu(builder.getRootMenu());
                }
            } else {
                builder.getStoreAction().notFoundMenuItem();
            }
        }
    }
}
