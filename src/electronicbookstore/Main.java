package electronicbookstore;

import electronicbookstore.arraylist.OrderArrayList;
import electronicbookstore.storage.Book;
import electronicbookstore.store.BookOrder;
import electronicbookstore.store.Customer;

import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {
        OrderArrayList orderArrayList = new OrderArrayList(2);
        orderArrayList.add(new BookOrder(new Customer("1","111","hhhg"), new GregorianCalendar(), new Book("qwe","qsdfsd", 2015)));
        orderArrayList.add(new BookOrder(new Customer("2","111","hhhg"), new GregorianCalendar(), new Book("qwe","qsdfsd", 2015)));
        System.out.println(orderArrayList.size());
        orderArrayList.add(new BookOrder(new Customer("3","111","hhhg"), new GregorianCalendar(), new Book("qwe","qsdfsd", 2015)));
        orderArrayList.add(new BookOrder(new Customer("4","111","hhhg"), new GregorianCalendar(), new Book("qwe","qsdfsd", 2015)));
        orderArrayList.add(new BookOrder(new Customer("5","111","hhhg"), new GregorianCalendar(), new Book("qwe","qsdfsd", 2015)));
        orderArrayList.add(new BookOrder(new Customer("6","111","hhhg"), new GregorianCalendar(), new Book("qwe","qsdfsd", 2015)));
        orderArrayList.add(new BookOrder(new Customer("7","111","hhhg"), new GregorianCalendar(), new Book("qwe","qsdfsd", 2015)));
        System.out.println(orderArrayList.size());
        orderArrayList.add(new BookOrder(new Customer("8","111","hhhg"), new GregorianCalendar(), new Book("qwe","qsdfsd", 2015)));
        orderArrayList.add(new BookOrder(new Customer("9","111","hhhg"), new GregorianCalendar(), new Book("qwe","qsdfsd", 2015)));
        orderArrayList.add(new BookOrder(new Customer("10","111","hhhg"), new GregorianCalendar(), new Book("qwe","qsdfsd", 2015)));
        orderArrayList.add(new BookOrder(new Customer("11","111","hhhg"), new GregorianCalendar(), new Book("qwe","qsdfsd", 2015)));
        orderArrayList.add(new BookOrder(new Customer("12","111","hhhg"), new GregorianCalendar(), new Book("qwe","qsdfsd", 2015)));
        orderArrayList.add(new BookOrder(new Customer("13","111","hhhg"), new GregorianCalendar(), new Book("qwe","qsdfsd", 2015)));
        System.out.println(orderArrayList.size());
        System.out.println(orderArrayList);
    }
}
