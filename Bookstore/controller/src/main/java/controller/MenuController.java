package controller;

import controller.model.Menu;
import com.annotation.InjectByType;


public class MenuController {

    @InjectByType
    private Builder builder;
    @InjectByType
    private Navigator navigator;

    public MenuController() {
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
