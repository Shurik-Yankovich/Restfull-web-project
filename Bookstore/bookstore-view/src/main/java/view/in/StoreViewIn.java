package view.in;

import entity.Bookshelf;
import entity.Customer;
import com.annotation.Singleton;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Singleton
public class StoreViewIn implements ViewIn {

    private static final String MENU_TEXT = "%d - %s";

    private Scanner scanner;

    public StoreViewIn() {
        scanner = new Scanner(System.in);
    }

    @Override
    public LocalDate readDateFrom() {
        System.out.println("Введите период дат в формате \"дд мм гггг\":");
        System.out.println("Начало периода:");
        return getDateFromConsole();
    }

    @Override
    public LocalDate readDateTo() {
        System.out.println("Окончание периода:");
        return getDateFromConsole();
    }

    @Override
    public Customer readCustomer() {
        System.out.println("Введите ваше ФИО:");
        String name = readStringFromConsole();
        System.out.println("Введите ваш адрес:");
        String address = readStringFromConsole();
        System.out.println("Введите номер телефона:");
        String phone = readStringFromConsole();
        return new Customer(name, phone, address);
    }

    @Override
    public List<Integer> readBookList(List<Bookshelf> bookshelves) {
        printList(bookshelves);
        System.out.println("Выбирете книги из списка (для завершения формирования списка введите -1):");
        List<Integer> bookNumbers = new ArrayList<>();
        int number = readIntFromConsole();
        while (number != -1) {
            if (number >= 0 && number < bookshelves.size()) {
                bookNumbers.add(number);
            } else {
                System.err.println("Неверно выбран номер книги из списка!");
            }
            number = readIntFromConsole();
        }
        return bookNumbers;
    }

    @Override
    public <T> T choiceFromList(List<T> list) {
        printList(list);
        int choice;
        boolean isSelection = false;
        if (list.size() > 0) {
            do {
                System.out.println("Выбирете пункт из списка:");
                choice = readIntFromConsole();
                if (choice >= 0 && choice < list.size()) {
                    isSelection = true;
                } else {
                    System.err.println("Неверно выбран пункт из списка!");
                }
            } while (!isSelection);
            return list.get(choice);
        } else {
            System.out.println("Список пуст!");
            return null;
        }
    }

    @Override
    public int readCountBook() {
        System.out.println("Введите количество книг:");
        return readIntFromConsole();
    }

    @Override
    public int readIntFromConsole() {
        while (true) {
            try {
                int number = scanner.nextInt();
                scanner.nextLine();
                return number;
            } catch (InputMismatchException e) {
                System.err.println("Неверно введены данные! Пожалуйста введите число!");
                scanner.nextLine();
            }
        }
    }

    @Override
    public int saveChanging() {
        System.out.println("Записать изменения в файл:\n0 - Да\n1 - Нет");
        return readIntFromConsole();
    }

    private String readStringFromConsole() {
        return scanner.nextLine();
    }

    private <T> void printList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(String.format(MENU_TEXT, i, list.get(i)));
        }
    }

    private LocalDate getDateFromConsole() {
        String dateString;
        LocalDate date = null;
        boolean isFormatting = false;
        do {
            dateString = readStringFromConsole();
            try {
                date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd MM yyyy"));
                isFormatting = true;
            } catch (DateTimeException e) {
                System.err.println("Неверно введена дата! Повторите ввод!");
            }
        } while (!isFormatting);
        return date;
    }

}
