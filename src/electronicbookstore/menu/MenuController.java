package electronicbookstore.menu;

public class MenuController {

    private Builder builder;
    private Navigator navigator;

    public MenuController() {
        this.builder = new Builder();
        this.navigator = new Navigator(new Menu("", new MenuItem[5]));
    }

    public void run(){
        builder.buildMenu();
        navigator.printMenu();
    }
}
