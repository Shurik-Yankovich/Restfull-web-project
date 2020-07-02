package bookstore.view;

import bookstore.model.Bookshelf;
import bookstore.model.Customer;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewIn {

    private static final String MENU_TEXT = "%d - %s";

    private Scanner scanner;

    public ViewIn() {
        scanner = new Scanner(System.in);
    }

    public LocalDate readDateFrom() {
        System.out.println("Введите период дат в формате \"дд мм гггг\":");
        System.out.println("Начало периода:");
        return getDateFromConsole();
    }

    public LocalDate readDateTo() {
        System.out.println("Окончание периода:");
        return getDateFromConsole();
    }

    public Customer readCustomer() {
        System.out.println("Введите ваше ФИО:");
        String name = readStringFromConsole();
        System.out.println("Введите ваш адрес:");
        String address = readStringFromConsole();
        System.out.println("Введите номер телефона:");
        String phone = readStringFromConsole();
        return new Customer(name, phone, address);
    }

    public List<Integer> readBookList(List<Bookshelf> bookshelves){
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

    public int readCountBook() {
        System.out.println("Введите количество книг:");
        return readIntFromConsole();
    }


    public int readIntFromConsole() {
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    private String readStringFromConsole() {
        return scanner.nextLine();
    }

    private  <T> void printList(List<T> list) {
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
            } catch (DateTimeException e){
                System.err.println("Неверно введена дата! Повторите ввод!");
            }
        } while (!isFormatting);
        return date;
    }

}
