package bookstore.controller.builder;

import bookstore.controller.model.Menu;

public interface Buildable {

    void buildMenu();
    Menu getRootMenu();
}