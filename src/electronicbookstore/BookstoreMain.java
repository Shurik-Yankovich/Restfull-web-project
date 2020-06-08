package electronicbookstore;

import electronicbookstore.storage.Book;
import electronicbookstore.storage.BookStorage;
import electronicbookstore.storage.Bookshelf;
import electronicbookstore.storage.Storage;
import electronicbookstore.store.Customer;
import electronicbookstore.store.ElectronicBookstore;
import electronicbookstore.store.Store;
import electronicbookstore.store.arrays.BookOrder;
import electronicbookstore.util.BookGenerator;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class BookstoreMain {

    public static void main(String[] args) {

        Bookshelf[] bookshelves = BookGenerator.generate();
        Book book = bookshelves[5].getBook();
        Storage bookStorage = new BookStorage(bookshelves);
        Store bookstore = new ElectronicBookstore(bookStorage);
        Customer customer1 = new Customer("Иванов Иван Иванович", "+375333753753", "г. Брест, ул. Пушкина, д. 49");
        Customer customer2 = new Customer("Петров Петр Петрович", "+375299999999", "г. Брест, ул. Моськина, д. 120б кв.50");
        BookOrder order1 = new BookOrder(customer1, bookshelves[5].getBook(), bookshelves[1].getBook());
        BookOrder order2 = new BookOrder(customer2, bookshelves[9].getBook(), bookshelves[3].getBook(), bookshelves[5].getBook());
        BookOrder order3 = new BookOrder(customer1, bookshelves[9].getBook(), bookshelves[5].getBook(), bookshelves[0].getBook());

        bookstore.addOrder(order1);
        bookstore.addOrder(order2);
        bookstore.addOrder(order3);

        System.out.println(Arrays.toString(bookstore.getBookList()) + "\n");

        System.out.println(order1 + "\nбыл выполнен - " + bookstore.completeOrder(order1) + "\n");
        System.out.println(order2 + "\nбыл выполнен - " + bookstore.completeOrder(order2) + "\n");
        bookstore.addBookOnStorage(book);
        System.out.println(order2 + "\nбыл выполнен - " + bookstore.completeOrder(order2) + "\n");

        System.out.println(Arrays.toString(bookstore.getRequestList()) + "\n");
        bookstore.cancelOrder(order3);
        System.out.println(Arrays.toString(bookstore.getRequestList()) + "\n");

        Calendar dateFrom = new GregorianCalendar();
        dateFrom.add(Calendar.MONTH, -2);
        Calendar dateTo = new GregorianCalendar();
        System.out.println("Заработано денег: " + bookstore.earnedMoney(dateFrom, dateTo) + "\n");
        System.out.println("Выполненные заказы: " + Arrays.toString(bookstore.getCompletedOrderList(dateFrom, dateTo)) + "\nИх кол-во = " + bookstore.getCountCompletedOrder(dateFrom, dateTo) + "\n");

        System.out.println("Список залежавшихся книг:\n" + Arrays.toString(bookstore.getUnsoldBookList()) + "\n");

    }

}
