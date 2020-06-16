package electronicbookstore.menu.builder;

import electronicbookstore.menu.model.Menu;

public interface Buildable {

    void buildMenu();
    Menu getRootMenu();
}
