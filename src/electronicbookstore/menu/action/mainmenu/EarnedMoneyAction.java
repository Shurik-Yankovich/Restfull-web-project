package electronicbookstore.menu.action.mainmenu;

import electronicbookstore.menu.action.Action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static electronicbookstore.menu.Console.console;
import static electronicbookstore.service.store.ElectronicBookstore.bookstore;

public class EarnedMoneyAction implements Action {
    @Override
    public void execute() {
        System.out.println("Введите период дат в формате \"дд мм гггг\":");
        String dateFrom = console.readStringFromConsole();
        String dateTo = console.readStringFromConsole();
        double money = bookstore.earnedMoney(LocalDate.parse(dateFrom, DateTimeFormatter.ofPattern("dd MM yyyy")),
                LocalDate.parse(dateTo, DateTimeFormatter.ofPattern("dd MM yyyy")));
        System.out.println("За данный промежуток времени заработано: " + money);
    }
}
