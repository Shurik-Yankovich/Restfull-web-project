package bookstore.controller;


import bookstore.controller.model.Menu;
import bookstore.controller.model.MenuItem;

public class Navigator {

    private Menu currentMenu;

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void printMenu() {
        MenuItem[] menuItems = currentMenu.getMenuItems();
        System.out.println();
        for (MenuItem menuItem : menuItems) {
            System.out.println(menuItem.getTitle());
        }
    }

    public void navigate(Integer index) {
        MenuItem menuItem = currentMenu.getMenuItems()[index];
        if (menuItem.getNextMenu() == null) {
            menuItem.doAction();
        } else {
            currentMenu = menuItem.getNextMenu();
        }
    }
}
