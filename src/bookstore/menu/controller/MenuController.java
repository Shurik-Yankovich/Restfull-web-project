package bookstore.menu.controller;

import bookstore.menu.builder.Buildable;
import bookstore.menu.navigator.Navigable;
import bookstore.menu.navigator.Navigator;
import bookstore.menu.builder.Builder;
import bookstore.menu.model.Menu;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;

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
            choice = CONSOLE_WORKER.readIntFromConsole();
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
