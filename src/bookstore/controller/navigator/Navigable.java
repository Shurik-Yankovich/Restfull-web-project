package bookstore.controller.navigator;

import bookstore.controller.model.Menu;

public interface Navigable {

    Menu getCurrentMenu();
    void setCurrentMenu(Menu currentMenu);
    void printMenu();
    void navigate(Integer index);
}
