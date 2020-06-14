package electronicbookstore.menu;

public class Navigator {

    private Menu currentMenu;

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void printMenu() {
        MenuItem[] menuItems = currentMenu.getMenuItems();
        for (MenuItem menuItem : menuItems) {
            System.out.println(menuItem.getTitle());
        }
    }

    public void navigate(Integer index) {
        MenuItem menuItem = currentMenu.getMenuItems()[index];
        if (menuItem.getNextMenu() == null) {
            menuItem.doAction();
        } else {
            currentMenu = menuItem.getNextMenu();
        }
    }
}
