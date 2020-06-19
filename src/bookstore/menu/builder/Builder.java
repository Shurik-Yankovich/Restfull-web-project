package bookstore.menu.builder;

import bookstore.menu.action.mainmenu.EarnedMoneyAction;
import bookstore.menu.action.mainmenu.ExitAction;
import bookstore.menu.action.ordermenu.*;
import bookstore.menu.action.requestmenu.AddRequestAction;
import bookstore.menu.action.requestmenu.SortingRequestsAction;
import bookstore.menu.action.requestmenu.UnsortingRequestAction;
import bookstore.menu.action.storagemenu.AddBookAction;
import bookstore.menu.action.storagemenu.ShowUnsoldBooks;
import bookstore.menu.action.storagemenu.SortingBooksAction;
import bookstore.menu.action.storagemenu.UnsortingBooksAction;
import bookstore.menu.constant.mainmenu.MainMenu;
import bookstore.menu.constant.mainmenu.MainMenuTextConst;
import bookstore.menu.constant.ordermenu.OrderListMenu;
import bookstore.menu.constant.ordermenu.OrderListMenuTextConst;
import bookstore.menu.constant.ordermenu.OrderMenu;
import bookstore.menu.constant.ordermenu.OrderMenuTextConst;
import bookstore.menu.constant.requestmenu.RequestListMenu;
import bookstore.menu.constant.requestmenu.RequestListMenuTextConst;
import bookstore.menu.constant.requestmenu.RequestMenu;
import bookstore.menu.constant.requestmenu.RequestMenuTextConst;
import bookstore.menu.constant.storagemenu.BookListMenu;
import bookstore.menu.constant.storagemenu.BookListMenuTextConst;
import bookstore.menu.constant.storagemenu.StorageMenu;
import bookstore.menu.constant.storagemenu.StorageMenuTextConst;
import bookstore.menu.model.Menu;
import bookstore.menu.model.MenuItem;


public class Builder implements Buildable {

    private static final String MENU_TEXT = "%d - %s";

    private Menu rootMenu;

    @Override
    public Menu getRootMenu() {
        return rootMenu;
    }

    @Override
    public void buildMenu() {
        buildMainMenu();
    }

    private void buildMainMenu() {
        rootMenu = new Menu();
        MainMenu[] menuActions = MainMenu.values();
        int countMenuItems = menuActions.length;
        MenuItem[] menuItems = new MenuItem[countMenuItems];

        for (int i = 0; i < countMenuItems; i++) {
            menuItems[i] = getMainMenuItem(i, menuActions[i]);
        }
        rootMenu.setMenuItems(menuItems);
        rootMenu.setName("Главное меню");
    }

    private MenuItem getMainMenuItem(int index, MainMenu menuAction) {
        switch (menuAction) {
            case WORK_WITH_STORAGE:
                return new MenuItem(String.format(MENU_TEXT, index, MainMenuTextConst.ITEM_TEXT_WORK_WITH_STORAGE),
                        null, buildStorageMenu());
            case WORK_WITH_ORDER:
                return new MenuItem(String.format(MENU_TEXT, index, MainMenuTextConst.ITEM_TEXT_WORK_WITH_ORDER),
                        null, buildOrderMenu());
            case WORK_WITH_REQUEST:
                return new MenuItem(String.format(MENU_TEXT, index, MainMenuTextConst.ITEM_TEXT_WORK_WITH_REQUEST),
                        null, buildRequestMenu());
            case COUNT_EARNED_MONEY:
                return new MenuItem(String.format(MENU_TEXT, index, MainMenuTextConst.ITEM_TEXT_COUNT_EARNED_MONEY),
                        new EarnedMoneyAction(), null);
            case EXIT:
                return new MenuItem(String.format(MENU_TEXT, index, MainMenuTextConst.ITEM_TEXT_EXIT),
                        new ExitAction(), null);
        }
        return null;
    }

    private Menu buildStorageMenu() {
        Menu menu = new Menu();
        StorageMenu[] menuActions = StorageMenu.values();
        int countMenuItems = menuActions.length;
        MenuItem[] menuItems = new MenuItem[countMenuItems];

        for (int i = 0; i < countMenuItems; i++) {
            menuItems[i] = getStorageMenuItem(i, menuActions[i]);
        }
        menu.setMenuItems(menuItems);
        menu.setName("Меню работы со складом");
        return menu;
    }

    private MenuItem getStorageMenuItem(int index, StorageMenu menuAction) {
        switch (menuAction) {
            case ADD_BOOK:
                return new MenuItem(String.format(MENU_TEXT, index, StorageMenuTextConst.ITEM_TEXT_ADD_BOOK),
                        new AddBookAction(), null);
            case SHOW_BOOK_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, StorageMenuTextConst.ITEM_TEXT_SHOW_BOOK_LIST),
                        null, buildBookListMenu());
            case SHOW_UNSOLD_BOOK_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, StorageMenuTextConst.ITEM_TEXT_SHOW_UNSOLD_BOOK_LIST),
                        new ShowUnsoldBooks(), null);
            case BACK:
                return new MenuItem(String.format(MENU_TEXT, index, StorageMenuTextConst.ITEM_TEXT_BACK),
                        null, rootMenu);
        }
        return null;
    }

    private Menu buildBookListMenu() {
        Menu menu = new Menu();
        BookListMenu[] menuActions = BookListMenu.values();
        int countMenuItems = menuActions.length;
        MenuItem[] menuItems = new MenuItem[countMenuItems];

        for (int i = 0; i < countMenuItems; i++) {
            menuItems[i] = getBookListMenuItem(i, menuActions[i]);
        }
        menu.setMenuItems(menuItems);
        menu.setName("Меню отображения списка книг");
        return menu;
    }

    private MenuItem getBookListMenuItem(int index, BookListMenu menuAction) {
        switch (menuAction) {
            case BOOK_LIST_WITH_SORT:
                return new MenuItem(String.format(MENU_TEXT, index, BookListMenuTextConst.ITEM_TEXT_BOOK_LIST_WITH_SORT),
                        new SortingBooksAction(), null);
            case BOOK_LIST_WITHOUT_SORT:
                return new MenuItem(String.format(MENU_TEXT, index, BookListMenuTextConst.ITEM_TEXT_BOOK_LIST_WITHOUT_SORT),
                        new UnsortingBooksAction(), null);
            case RETURN_TO_MAIN_MENU:
                return new MenuItem(String.format(MENU_TEXT, index, BookListMenuTextConst.ITEM_TEXT_RETURN_TO_MAIN_MENU),
                        null, rootMenu);
        }
        return null;
    }

    private Menu buildOrderMenu() {
        Menu menu = new Menu();
        OrderMenu[] menuActions = OrderMenu.values();
        int countMenuItems = menuActions.length;
        MenuItem[] menuItems = new MenuItem[countMenuItems];

        for (int i = 0; i < countMenuItems; i++) {
            menuItems[i] = getOrderMenuItem(i, menuActions[i]);
        }
        menu.setMenuItems(menuItems);
        menu.setName("Меню работы с заказами");
        return menu;
    }

    private MenuItem getOrderMenuItem(int index, OrderMenu menuAction) {
        switch (menuAction) {
            case ADD_ORDER:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_ADD_ORDER),
                        new AddOrderAction(), null);
            case CANCEL_ORDER:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_CANCEL_ORDER),
                        new CancelOrderAction(), null);
            case COMPLETE_ORDER:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_COMPLETE_ORDER),
                        new CompleteOrderAction(), null);
            case SHOW_ORDER_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_SHOW_ORDER_LIST),
                        null, buildOrderListMenu());
            case SHOW_COMPLETED_ORDER_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_SHOW_COMPLETED_ORDER_LIST),
                        new ShowCompletedOrdersAction(), null);
            case SHOW_COUNT_COMPLETED_ORDER:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_SHOW_COUNT_COMPLETED_ORDER),
                        new CountCompletedOrderAction(), null);
            case BACK:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_BACK),
                        null, rootMenu);
        }
        return null;
    }

    private Menu buildOrderListMenu() {
        Menu menu = new Menu();
        OrderListMenu[] menuActions = OrderListMenu.values();
        int countMenuItems = menuActions.length;
        MenuItem[] menuItems = new MenuItem[countMenuItems];

        for (int i = 0; i < countMenuItems; i++) {
            menuItems[i] = getOrderListMenuItem(i, menuActions[i]);
        }
        menu.setMenuItems(menuItems);
        menu.setName("Меню отображения списка заказов");
        return menu;
    }

    private MenuItem getOrderListMenuItem(int index, OrderListMenu menuAction) {
        switch (menuAction) {
            case ORDER_LIST_WITH_SORT:
                return new MenuItem(String.format(MENU_TEXT, index, OrderListMenuTextConst.ITEM_TEXT_ORDER_LIST_WITH_SORT),
                        new SortingOrdersAction(), null);
            case ORDER_LIST_WITHOUT_SORT:
                return new MenuItem(String.format(MENU_TEXT, index, OrderListMenuTextConst.ITEM_TEXT_ORDER_LIST_WITHOUT_SORT),
                        new UnsortingOrdersAction(), null);
            case RETURN_TO_MAIN_MENU:
                return new MenuItem(String.format(MENU_TEXT, index, OrderListMenuTextConst.ITEM_TEXT_RETURN_TO_MAIN_MENU),
                        null, rootMenu);
        }
        return null;
    }

    private Menu buildRequestMenu() {
        Menu menu = new Menu();
        RequestMenu[] menuActions = RequestMenu.values();
        int countMenuItems = menuActions.length;
        MenuItem[] menuItems = new MenuItem[countMenuItems];

        for (int i = 0; i < countMenuItems; i++) {
            menuItems[i] = getRequestMenuItem(i, menuActions[i]);
        }
        menu.setMenuItems(menuItems);
        menu.setName("Меню работы с запросами");
        return menu;
    }

    private MenuItem getRequestMenuItem(int index, RequestMenu menuAction) {
        switch (menuAction) {
            case ADD_REQUEST:
                return new MenuItem(String.format(MENU_TEXT, index, RequestMenuTextConst.ITEM_TEXT_ADD_REQUEST),
                        new AddRequestAction(), null);
            case SHOW_REQUEST_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, RequestMenuTextConst.ITEM_TEXT_SHOW_REQUEST_LIST),
                        null, buildRequestListMenu());
            case BACK:
                return new MenuItem(String.format(MENU_TEXT, index, RequestMenuTextConst.ITEM_TEXT_BACK),
                        null, rootMenu);
        }
        return null;
    }

    private Menu buildRequestListMenu() {
        Menu menu = new Menu();
        RequestListMenu[] menuActions = RequestListMenu.values();
        int countMenuItems = menuActions.length;
        MenuItem[] menuItems = new MenuItem[countMenuItems];

        for (int i = 0; i < countMenuItems; i++) {
            menuItems[i] = getRequestListMenuItem(i, menuActions[i]);
        }
        menu.setMenuItems(menuItems);
        menu.setName("Меню отображения списка запросов");
        return menu;
    }

    private MenuItem getRequestListMenuItem(int index, RequestListMenu menuAction) {
        switch (menuAction) {
            case REQUEST_LIST_WITH_SORT:
                return new MenuItem(String.format(MENU_TEXT, index, RequestListMenuTextConst.ITEM_TEXT_REQUEST_LIST_WITH_SORT),
                        new SortingRequestsAction(), null);
            case REQUEST_LIST_WITHOUT_SORT:
                return new MenuItem(String.format(MENU_TEXT, index, RequestListMenuTextConst.ITEM_TEXT_REQUEST_LIST_WITHOUT_SORT),
                        new UnsortingRequestAction(), null);
            case RETURN_TO_MAIN_MENU:
                return new MenuItem(String.format(MENU_TEXT, index, RequestListMenuTextConst.ITEM_TEXT_RETURN_TO_MAIN_MENU),
                        null, rootMenu);
        }
        return null;
    }
}
