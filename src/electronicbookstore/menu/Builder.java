package electronicbookstore.menu;

import electronicbookstore.menu.constant.*;
import electronicbookstore.model.Bookshelf;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static electronicbookstore.service.store.ElectronicBookstore.bookstore;


public class Builder {

    private static final String MENU_TEXT = "%d - %s";
    private Scanner scanner;

    private Menu rootMenu;

    public Builder() {
        scanner = new Scanner(System.in);
    }

    public Menu getRootMenu() {
        return rootMenu;
    }

    public void buildMenu() {
        buildMainMenu();
    }

    private void buildMainMenu() {
        rootMenu = new Menu();
        MenuItem[] menuItems = new MenuItem[5];

        for (int i = 0; i < MainMenuAction.values().length; i++) {
            menuItems[i] = getMainMenuItem(i, MainMenuAction.values()[i]);
        }

        rootMenu.setMenuItems(menuItems);
        rootMenu.setName("Главное меню");
    }

    private MenuItem getMainMenuItem(int index, MainMenuAction mainMenuAction) {
        switch (mainMenuAction) {
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
                        () -> {
                            System.out.println("Введите период дат в формате \"дд мм гггг\":");
                            String dateFrom = readStringFromConsole();
                            String dateTo = readStringFromConsole();
                            double money = bookstore.earnedMoney(LocalDate.parse(dateFrom, DateTimeFormatter.ofPattern("dd MM yyyy")),
                                    LocalDate.parse(dateTo, DateTimeFormatter.ofPattern("dd MM yyyy")));
                            System.out.println("За данный промежуток времени заработано: " + money);
                        }, null);
            case EXIT:
                return new MenuItem(String.format(MENU_TEXT, index, MainMenuTextConst.ITEM_TEXT_EXIT),
                        () -> System.exit(1), null);
        }
        return null;
    }

    private Menu buildStorageMenu() {
        Menu menu = new Menu();
        MenuItem[] menuItems = new MenuItem[5];

        for (int i = 0; i < StorageMenuAction.values().length; i++) {
            menuItems[i] = getStorageMenuItem(i, StorageMenuAction.values()[i]);
        }

        menu.setMenuItems(menuItems);
        menu.setName("Меню работы со складом");
        return menu;
    }

    private MenuItem getStorageMenuItem(int index, StorageMenuAction storageMenuAction) {
        switch (storageMenuAction) {
            case ADD_BOOK:
                return new MenuItem(String.format(MENU_TEXT, index, StorageMenuTextConst.ITEM_TEXT_ADD_BOOK),
                        null, null);
            case SHOW_BOOK_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, StorageMenuTextConst.ITEM_TEXT_SHOW_BOOK_LIST),
                        null, null);
            case SHOW_UNSOLD_BOOK_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, StorageMenuTextConst.ITEM_TEXT_SHOW_UNSOLD_BOOK_LIST),
                        null, null);
            case SHOW_BOOK_DESCRIPTION:
                return new MenuItem(String.format(MENU_TEXT, index, StorageMenuTextConst.ITEM_TEXT_SHOW_BOOK_DESCRIPTION),
                        null, null);
            case BACK:
                return new MenuItem(String.format(MENU_TEXT, index, StorageMenuTextConst.ITEM_TEXT_BACK),
                        null, rootMenu);
        }
        return null;
    }

    private Menu buildOrderMenu() {
        Menu menu = new Menu();
        MenuItem[] menuItems = new MenuItem[7];

        for (int i = 0; i < OrderMenuAction.values().length; i++) {
            menuItems[i] = getOrderMenuItem(i, OrderMenuAction.values()[i]);
        }

        menu.setMenuItems(menuItems);
        menu.setName("Меню работы с заказами");
        return menu;
    }

    private MenuItem getOrderMenuItem(int index, OrderMenuAction orderMenuAction) {
        switch (orderMenuAction) {
            case ADD_ORDER:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_ADD_ORDER),
                        null, null);
            case CANCEL_ORDER:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_CANCEL_ORDER),
                        null, null);
            case COMPLETE_ORDER:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_COMPLETE_ORDER),
                        null, null);
            case SHOW_ORDER_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_SHOW_ORDER_LIST),
                        null, null);
            case SHOW_COMPLETED_ORDER_LIST:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_SHOW_COMPLETED_ORDER_LIST),
                        null, null);
            case SHOW_ORDER_DESCRIPTION:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_SHOW_ORDER_DESCRIPTION),
                        null, null);
            case BACK:
                return new MenuItem(String.format(MENU_TEXT, index, OrderMenuTextConst.ITEM_TEXT_BACK),
                        null, rootMenu);
        }
        return null;
    }

    private Menu buildRequestMenu() {
        Menu menu = new Menu();
        MenuItem[] menuItems = new MenuItem[4];

        for (int i = 0; i < RequestMenuAction.values().length; i++) {
            menuItems[i] = getRequestMenuItem(i, RequestMenuAction.values()[i]);
        }

        menu.setMenuItems(menuItems);
        menu.setName("Меню работы с запросами");
        return menu;
    }

    private MenuItem getRequestMenuItem(int index, RequestMenuAction requestMenuAction) {
        switch (requestMenuAction) {
            case ADD_REQUEST:
                return new MenuItem(String.format(MENU_TEXT, index, RequestMenuTextConst.ITEM_TEXT_ADD_REQUEST),
                        () -> {
                            List<Bookshelf> books = bookstore.getBookList();
                            printList(books);
                            System.out.println("Выбирете номер книги для добавления:");
                            bookstore.addRequest(books.get(readIntFromConsole()).getBook());
                        }, null);
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
        MenuItem[] menuItems = new MenuItem[4];

        for (int i = 0; i < RequestListMenuAction.values().length; i++) {
            menuItems[i] = getRequestListMenuItem(i, RequestListMenuAction.values()[i]);
        }

        menu.setMenuItems(menuItems);
        menu.setName("Меню отображения списка запросов");
        return menu;
    }

    private MenuItem getRequestListMenuItem(int index, RequestListMenuAction requestListMenuAction) {
        switch (requestListMenuAction) {
            case REQUEST_BOOK_NAME_SORT:
                return new MenuItem(String.format(MENU_TEXT, index, RequestListMenuTextConst.ITEM_TEXT_REQUEST_BOOK_NAME_SORT),
                        () -> System.out.println(bookstore.getSortingRequestList()), null);
            case REQUEST_COUNT_SORT:
                return new MenuItem(String.format(MENU_TEXT, index, RequestListMenuTextConst.ITEM_TEXT_REQUEST_COUNT_SORT),
                        () -> System.out.println(bookstore.getSortingRequestList()), null);
            case REQUEST_LIST_WITHOUT_SORT:
                return new MenuItem(String.format(MENU_TEXT, index, RequestListMenuTextConst.ITEM_TEXT_REQUEST_LIST_WITHOUT_SORT),
                        () -> System.out.println(bookstore.getRequestList()), null);
            case RETURN_TO_MAIN_MENU:
                return new MenuItem(String.format(MENU_TEXT, index, RequestListMenuTextConst.ITEM_TEXT_RETURN_TO_MAIN_MENU),
                        null, rootMenu);
        }
        return null;
    }

    private <T> void printList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(String.format(MENU_TEXT, i, list.get(i)));
        }
    }

    private int readIntFromConsole() {
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    private String readStringFromConsole() {
        return scanner.nextLine();
    }

}
