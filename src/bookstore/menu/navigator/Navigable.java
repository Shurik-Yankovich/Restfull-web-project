package bookstore.menu.navigator;

import bookstore.menu.model.Menu;

public interface Navigable {

    Menu getCurrentMenu();
    void setCurrentMenu(Menu currentMenu);
    void printMenu();
    void navigate(Integer index);
}
