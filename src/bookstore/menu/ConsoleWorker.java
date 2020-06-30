package bookstore.menu;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public <T> T choiceFromList(List<T> list) {
        printList(list);
        int choice;
        boolean isSelection = false;
        do {
            System.out.println("Выбирете пункт из списка:");
            choice = readIntFromConsole();
            if (choice >= 0 && choice < list.size()){
                isSelection = true;
            } else {
                System.err.println("Неверно выбран пункт из списка!");
            }
        } while (!isSelection);
        return list.get(choice);
    }

    public <T> void printList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(String.format(MENU_TEXT, i, list.get(i)));
        }
    }

    public LocalDate getDateFromConsole() {
        String dateString;
        LocalDate date = null;
        boolean isFormatting = false;
        do {
            dateString = readStringFromConsole();
            try {
                date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd MM yyyy"));
                isFormatting = true;
            } catch (DateTimeException e){
                System.err.println("Неверно введена дата! Повторите ввод!");
            }
        } while (!isFormatting);
        return date;
    }
}
