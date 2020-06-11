package electronicbookstore.menu;

public class Navigator {

    private Menu currentMenu;

    public Navigator(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void printMenu() {
        MenuItem[] menuItems = currentMenu.getMenuItems();
        for (int i = 0; i < menuItems.length; i++) {
            System.out.println(String.format("%d - %s", i, menuItems[i].getTitle()));
        }
    }

    public void navigate(Integer index) {
        currentMenu.getMenuItems()[index].doAction();
    }
}
