package bookstore.menu.builder;

import bookstore.menu.model.Menu;

public interface Buildable {

    void buildMenu();
    Menu getRootMenu();
}
