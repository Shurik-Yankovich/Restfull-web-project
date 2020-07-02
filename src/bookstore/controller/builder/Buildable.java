package bookstore.controller.builder;

import bookstore.controller.action.StoreAction;
import bookstore.controller.model.Menu;

public interface Buildable {

    void buildMenu();
    StoreAction getStoreAction();
    Menu getRootMenu();
}