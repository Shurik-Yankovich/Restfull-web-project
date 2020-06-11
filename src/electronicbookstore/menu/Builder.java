package electronicbookstore.menu;

public class Builder {

    private Menu rootMenu;

    public Builder(Menu rootMenu) {
        this.rootMenu = rootMenu;
    }

    public void buildMenu(){
    }

    public Menu getRootMenu(){
        return rootMenu;
    }
}
