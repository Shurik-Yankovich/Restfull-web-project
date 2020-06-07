package electronicbookstore;

import electronicbookstore.storage.BookStorage;
import electronicbookstore.storage.Bookshelf;
import electronicbookstore.storage.Storage;
import electronicbookstore.store.arrays.BookOrder;
import electronicbookstore.store.Customer;
import electronicbookstore.store.ElectronicBookstore;
import electronicbookstore.store.Store;
import electronicbookstore.util.BookGenerator;

import java.util.Arrays;
import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) {
        Bookshelf[] bookshelves = BookGenerator.generate();
        Storage bookStorage = new BookStorage(bookshelves);
        Store bookstore = new ElectronicBookstore(bookStorage);
        Customer customer = new Customer("Иванов Иван Иванович", "375333753753", "г. Брест, ул. Пушкина, д. 49");
        System.out.println(Arrays.toString(bookstore.getBookList()));
        BookOrder order = new BookOrder(customer, new GregorianCalendar(), bookshelves[5].getBook(), bookshelves[1].getBook());
        bookstore.addOrder(customer, bookshelves[5].getBook(), bookshelves[1].getBook());
        bookstore.completeOrder(order);
    }

}
