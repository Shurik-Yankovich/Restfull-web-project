package bookstore.controller.action.mainmenu;

import bookstore.controller.action.Action;

import java.time.LocalDate;

import static bookstore.view.ConsoleWorker.CONSOLE_WORKER;
import static bookstore.service.order.BookOrderService.ORDER_SERVICE;

public class EarnedMoneyAction implements Action {
    @Override
    public void execute() {
        System.out.println("Введите период дат в формате \"дд мм гггг\":");
        LocalDate dateFrom = CONSOLE_WORKER.getDateFromConsole();
        LocalDate dateTo = CONSOLE_WORKER.getDateFromConsole();
        double money = ORDER_SERVICE.earnedMoney(dateFrom, dateTo);
        System.out.println("За данный промежуток времени заработано: " + money);
    }
}
