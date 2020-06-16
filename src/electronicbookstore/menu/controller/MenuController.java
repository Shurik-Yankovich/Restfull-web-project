package electronicbookstore.menu.controller;

import electronicbookstore.menu.builder.Buildable;
import electronicbookstore.menu.navigator.Navigable;
import electronicbookstore.menu.navigator.Navigator;
import electronicbookstore.menu.builder.Builder;
import electronicbookstore.menu.model.Menu;

import static electronicbookstore.menu.Console.console;

public class MenuController implements Controller {

    private Buildable builder;
    private Navigable navigator;

    public MenuController() {
        builder = new Builder();
        navigator = new Navigator();
    }

    @Override
    public void run() {
        int choice;
        Menu currentMenu;

        builder.buildMenu();
        navigator.setCurrentMenu(builder.getRootMenu());
        while (true) {
            navigator.printMenu();
            choice = console.readIntFromConsole();
            currentMenu = navigator.getCurrentMenu();
            if (choice >= 0 && choice < currentMenu.getMenuItems().length) {
                navigator.navigate(choice);
                if (currentMenu.getName().equals(navigator.getCurrentMenu().getName())) {
                    navigator.setCurrentMenu(builder.getRootMenu());
                }
            } else {
                System.out.println("Неверно выбран пункт меню!\nПожалуйста выбирете нужный пункт меню:\n");
            }
        }
    }
}