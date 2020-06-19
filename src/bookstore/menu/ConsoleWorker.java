package bookstore.menu;

import java.util.List;
import java.util.Scanner;

public class ConsoleWorker {

    public static final ConsoleWorker CONSOLE_WORKER = new ConsoleWorker();

    private static final String MENU_TEXT = "%d - %s";

    private Scanner scanner;

    private ConsoleWorker() {
        scanner = new Scanner(System.in);
    }

    public int readIntFromConsole() {
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    public String readStringFromConsole() {
        return scanner.nextLine();
    }

    public  <T> T choiceFromList(List<T> list){
        printList(list);
        int choice;
        do {
            System.out.println("Выбирете пункт из списка:");
            choice = readIntFromConsole();
        } while (choice < 0 || choice >= list.size());
        return list.get(choice);
    }

    public  <T> void printList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(String.format(MENU_TEXT, i, list.get(i)));
        }
    }
}
