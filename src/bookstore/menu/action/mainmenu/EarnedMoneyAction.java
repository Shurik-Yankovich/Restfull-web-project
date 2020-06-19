package bookstore.menu.action.mainmenu;

import bookstore.menu.action.Action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static bookstore.menu.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.store.BookStoreService.bookstore;

public class EarnedMoneyAction implements Action {
    @Override
    public void execute() {
        System.out.println("Введите период дат в формате \"дд мм гггг\":");
        String dateFrom = CONSOLE_WORKER.readStringFromConsole();
        String dateTo = CONSOLE_WORKER.readStringFromConsole();
        double money = bookstore.earnedMoney(LocalDate.parse(dateFrom, DateTimeFormatter.ofPattern("dd MM yyyy")),
                LocalDate.parse(dateTo, DateTimeFormatter.ofPattern("dd MM yyyy")));
        System.out.println("За данный промежуток времени заработано: " + money);
    }
}
