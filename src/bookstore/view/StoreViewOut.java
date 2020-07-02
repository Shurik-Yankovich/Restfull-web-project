package bookstore.view;

import java.util.List;

public class StoreViewOut implements ViewOut{

    private static final String MENU_TEXT = "%d - %s";

    @Override
    public void earnedMoneyOut(double money) {
        System.out.println("За данный промежуток времени заработано: " + money);
    }

    @Override
    public void cancelOrderOut(boolean isCanceled) {
        if (isCanceled) {
            System.out.println("Заказ успешно отменен!");
        } else {
            System.out.println("Неудалось отменить заказ!");
        }
    }

    @Override
    public void completeOrderOut(boolean isCompleted) {
        if (isCompleted) {
            System.out.println("Заказ успешно завершен!");
        } else {
            System.out.println("Неудалось завершить заказ!");
        }
    }

    @Override
    public void countCompletedOrderOut(int countOrder) {
        System.out.println("За данный промежуток времени завершено " + countOrder + " заказов");
    }

    @Override
    public <T> void printList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(String.format(MENU_TEXT, i, list.get(i)));
        }
    }

    @Override
    public void notFoundMenuItem() {
        System.err.println("Неверно выбран пункт меню!\nПожалуйста выбирете нужный пункт меню:\n");
    }
}
