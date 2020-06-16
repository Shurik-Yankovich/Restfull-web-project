package electronicbookstore;

import electronicbookstore.model.Book;
import electronicbookstore.repository.storage.BookStorage;
import electronicbookstore.model.Bookshelf;
import electronicbookstore.repository.storage.Storage;
import electronicbookstore.model.Customer;
import electronicbookstore.service.store.ElectronicBookstore;
import electronicbookstore.service.store.Store;
import electronicbookstore.model.Order;
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
        Customer customer1 = new Customer("John Smith", "+375333753753", "London, Shaftesbury Avenue, 50");
        Customer customer2 = new Customer("Brad Pit", "+375299999999", "London, King's Road, 13");
        Order order1 = new Order(customer1, bookshelves[5].getBook(), bookshelves[1].getBook());
        Order order2 = new Order(customer2, bookshelves[9].getBook(), bookshelves[3].getBook(), bookshelves[5].getBook());
        Order order3 = new Order(customer1, bookshelves[9].getBook(), bookshelves[5].getBook(), bookshelves[0].getBook());

        System.out.println(Arrays.toString(bookstore.getBookList()) + "\n");

        bookstore.addOrder(order1);
        bookstore.addOrder(order2);
        bookstore.addOrder(order3);

        System.out.println(order1 + "\nwas performed - " + bookstore.completeOrder(order1) + "\n");
        System.out.println(order2 + "\nwas performed - " + bookstore.completeOrder(order2) + "\n");
        bookstore.addBookOnStorage(book);
        System.out.println(order2 + "\nwas performed - " + bookstore.completeOrder(order2) + "\n");

        System.out.println(Arrays.toString(bookstore.getRequestList()) + "\n");
        bookstore.cancelOrder(order3);
        System.out.println(Arrays.toString(bookstore.getRequestList()) + "\n");

        Calendar dateFrom = new GregorianCalendar();
        dateFrom.add(Calendar.MONTH, -2);
        Calendar dateTo = new GregorianCalendar();
        dateFrom.add(Calendar.DATE, 1);
        System.out.println("Earned money: " + bookstore.earnedMoney(dateFrom, dateTo) + "\n");
        System.out.println("Completed orders: " + Arrays.toString(bookstore.getCompletedOrderList(dateFrom, dateTo)) + "\nTheir number = " + bookstore.getCountCompletedOrder(dateFrom, dateTo) + "\n");

        System.out.println(Arrays.toString(bookstore.getBookList()) + "\n");

        System.out.println("List of unsold books:\n" + Arrays.toString(bookstore.getUnsoldBookList()) + "\n");

    }

}
