package electronicbookstore.menu.navigator;

import electronicbookstore.menu.model.Menu;

public interface Navigable {

    Menu getCurrentMenu();
    void setCurrentMenu(Menu currentMenu);
    void printMenu();
    void navigate(Integer index);
}
