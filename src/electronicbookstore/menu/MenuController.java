package electronicbookstore.menu;

public class MenuController {

    private Builder builder;
    private Navigator navigator;

    public MenuController(Builder builder, Navigator navigator) {
        this.builder = builder;
        this.navigator = navigator;
    }

    public void run(){
        builder.buildMenu();
        navigator.printMenu();
    }
}
