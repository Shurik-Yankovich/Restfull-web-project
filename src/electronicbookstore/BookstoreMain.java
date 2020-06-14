package electronicbookstore;

import electronicbookstore.menu.MenuController;

public class BookstoreMain {

    public static void main(String[] args) {

//        List<Bookshelf> bookshelves = BookGenerator.generate();
//        Book book = bookshelves.get(5).getBook();
//        Storage bookStorage = new BookStorage(bookshelves);
////        Store bookstore = new ElectronicBookstore(bookStorage);
//        Customer customer1 = new Customer("John Smith", "+375333753753", "London, Shaftesbury Avenue, 50");
//        Customer customer2 = new Customer("Brad Pit", "+375299999999", "London, King's Road, 13");
//        Order order1 = new Order(customer1, bookshelves.get(5).getBook(), bookshelves.get(1).getBook());
//        Order order2 = new Order(customer2, bookshelves.get(9).getBook(), bookshelves.get(3).getBook(), bookshelves.get(5).getBook());
//        Order order3 = new Order(customer1, bookshelves.get(9).getBook(), bookshelves.get(5).getBook(), bookshelves.get(0).getBook());
//
//        System.out.println(bookstore.getBookList() + "\n");
//
//        bookstore.addOrder(order1);
//        bookstore.addOrder(order2);
//        bookstore.addOrder(order3);
//
//        System.out.println(order1 + "\nwas performed - " + bookstore.completeOrder(order1) + "\n");
//        System.out.println(order2 + "\nwas performed - " + bookstore.completeOrder(order2) + "\n");
//        bookstore.addBookOnStorage(book);
//        System.out.println(order2 + "\nwas performed - " + bookstore.completeOrder(order2) + "\n");
//
//        System.out.println(bookstore.getRequestList() + "\n");
//        bookstore.cancelOrder(order3);
//        System.out.println(bookstore.getRequestList() + "\n");
//
//        LocalDate dateFrom = LocalDate.now().minusMonths(2);
//        LocalDate dateTo = LocalDate.now().plusDays(1);
//        System.out.println("Earned money: " + bookstore.earnedMoney(dateFrom, dateTo) + "\n");
//        System.out.println("Completed orders: " + bookstore.getCompletedOrderList(dateFrom, dateTo) + "\nTheir number = " + bookstore.getCountCompletedOrder(dateFrom, dateTo) + "\n");
//
//        System.out.println(bookstore.getBookList() + "\n");
//
//        System.out.println("List of unsold books:\n" + bookstore.getUnsoldBookList() + "\n");

        MenuController menuController = new MenuController();
        menuController.run();

    }

}
