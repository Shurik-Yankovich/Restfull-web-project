package bookstore.controller.navigator;


import bookstore.controller.model.Menu;
import bookstore.controller.model.MenuItem;

public class Navigator implements Navigable {

    private Menu currentMenu;

    @Override
    public Menu getCurrentMenu() {
        return currentMenu;
    }

    @Override
    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    @Override
    public void printMenu() {
        MenuItem[] menuItems = currentMenu.getMenuItems();
        System.out.println();
        for (MenuItem menuItem : menuItems) {
            System.out.println(menuItem.getTitle());
        }
    }

    @Override
    public void navigate(Integer index) {
        MenuItem menuItem = currentMenu.getMenuItems()[index];
        if (menuItem.getNextMenu() == null) {
            menuItem.doAction();
        } else {
            currentMenu = menuItem.getNextMenu();
        }
    }
}
