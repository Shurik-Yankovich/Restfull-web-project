package controller;

import controller.action.StoreAction;
import constant.mainmenu.MainMenu;
import constant.mainmenu.MainMenuTextConst;
import constant.ordermenu.OrderListMenu;
import constant.ordermenu.OrderListMenuTextConst;
import constant.ordermenu.OrderMenu;
import constant.ordermenu.OrderMenuTextConst;
import constant.requestmenu.RequestListMenu;
import constant.requestmenu.RequestListMenuTextConst;
import constant.requestmenu.RequestMenu;
import constant.requestmenu.RequestMenuTextConst;
import constant.storagemenu.BookListMenu;
import constant.storagemenu.BookListMenuTextConst;
import constant.storagemenu.StorageMenu;
import constant.storagemenu.StorageMenuTextConst;
import controller.model.Menu;
import controller.model.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;

public class Builder {

    private static final String MENU_TEXT = "%d - %s";

    private Menu rootMenu;
    @Autowired
    private StoreAction storeAction;

    public Builder() {
    }

    public Menu getRootMenu() {
        return rootMenu;
    }

    public StoreAction getStoreAction() {
        return storeAction;
    }

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
                        () -> storeAction.earnedMoneyAction(), null);
            case EXIT:
                return new MenuItem(String.format(MENU_TEXT, index, MainMenuTextConst.ITEM_TEXT_EXIT),
                        () -> storeAction.exitAction(), null);
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
                        () -> storeAction.addBookAction(), null);
            case SHOW_BOOK_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, StorageMenuTextConst.ITEM_TEXT_SHOW_BOOK_LIST),
                        null, buildBookListMenu());
            case SHOW_UNSOLD_BOOK_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, StorageMenuTextConst.ITEM_TEXT_SHOW_UNSOLD_BOOK_LIST),
                        () -> storeAction.showUnsoldBooks(), null);
            case EXPORT_BOOKSHELF:
                return new MenuItem(String.format(MENU_TEXT, index, StorageMenuTextConst.ITEM_TEXT_EXPORT_BOOKSHELF),
                        () -> storeAction.exportBookshelfAction(), null);
            case IMPORT_BOOKSHELF:
                return new MenuItem(String.format(MENU_TEXT, index, StorageMenuTextConst.ITEM_TEXT_IMPORT_BOOKSHELF),
                        () -> storeAction.importBookshelfAction(), null);
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
                        () -> storeAction.sortingBooksAction(), null);
            case BOOK_LIST_WITHOUT_SORT:
                return new MenuItem(String.format(MENU_TEXT, index, BookListMenuTextConst.ITEM_TEXT_BOOK_LIST_WITHOUT_SORT),
                        () -> storeAction.unsortingBooksAction(), null);
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
                        () -> storeAction.addOrderAction(), null);
            case CANCEL_ORDER:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_CANCEL_ORDER),
                        () -> storeAction.cancelOrderAction(), null);
            case COMPLETE_ORDER:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_COMPLETE_ORDER),
                        () -> storeAction.completeOrderAction(), null);
            case SHOW_ORDER_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_SHOW_ORDER_LIST),
                        null, buildOrderListMenu());
            case SHOW_COMPLETED_ORDER_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_SHOW_COMPLETED_ORDER_LIST),
                        () -> storeAction.showCompletedOrdersAction(), null);
            case SHOW_COUNT_COMPLETED_ORDER:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_SHOW_COUNT_COMPLETED_ORDER),
                        () -> storeAction.countCompletedOrderAction(), null);
            case EXPORT_ORDER:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_EXPORT_ORDER),
                        () -> storeAction.exportOrderAction(), null);
            case IMPORT_ORDER:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_IMPORT_ORDER),
                        () -> storeAction.importOrderAction(), null);
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
                        () -> storeAction.sortingOrdersAction(), null);
            case ORDER_LIST_WITHOUT_SORT:
                return new MenuItem(String.format(MENU_TEXT, index, OrderListMenuTextConst.ITEM_TEXT_ORDER_LIST_WITHOUT_SORT),
                        () -> storeAction.unsortingOrdersAction(), null);
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
                        () -> storeAction.addRequestAction(), null);
            case COMPLETE_REQUEST:
                return new MenuItem(String.format(MENU_TEXT, index, RequestMenuTextConst.ITEM_TEXT_COMPLETE_REQUEST),
                        () -> storeAction.completeRequestAction(), null);
            case SHOW_REQUEST_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, RequestMenuTextConst.ITEM_TEXT_SHOW_REQUEST_LIST),
                        null, buildRequestListMenu());
            case EXPORT_REQUEST:
                return new MenuItem(String.format(MENU_TEXT, index, RequestMenuTextConst.ITEM_TEXT_EXPORT_REQUEST),
                        () -> storeAction.exportRequestAction(), null);
            case IMPORT_REQUEST:
                return new MenuItem(String.format(MENU_TEXT, index, RequestMenuTextConst.ITEM_TEXT_IMPORT_REQUEST_LIST),
                        () -> storeAction.importRequestAction(), null);
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
                        () -> storeAction.sortingRequestsAction(), null);
            case REQUEST_LIST_WITHOUT_SORT:
                return new MenuItem(String.format(MENU_TEXT, index, RequestListMenuTextConst.ITEM_TEXT_REQUEST_LIST_WITHOUT_SORT),
                        () -> storeAction.unsortingRequestAction(), null);
            case RETURN_TO_MAIN_MENU:
                return new MenuItem(String.format(MENU_TEXT, index, RequestListMenuTextConst.ITEM_TEXT_RETURN_TO_MAIN_MENU),
                        null, rootMenu);
        }
        return null;
    }
}
