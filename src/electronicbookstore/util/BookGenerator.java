package electronicbookstore.util;

import electronicbookstore.storage.Book;
import electronicbookstore.storage.Bookshelf;

import java.util.GregorianCalendar;

public class BookGenerator {
    public static Bookshelf[] generate(){
        Bookshelf[] bookshelves = new Bookshelf[10];
        bookshelves[0] = new Bookshelf(new Book("Алхимик", "Пауло Коэльо", 2019), 14.22, new GregorianCalendar(2020, 1, 20));
        bookshelves[1] = new Bookshelf(new Book("Шантарам", "Гегори Дэвид Робертс", 2013), 29.02, new GregorianCalendar(2020, 3, 21));
        bookshelves[2] = new Bookshelf(new Book("Чистый код", "Роберт Мартин", 2016), 40.17, new GregorianCalendar(2020, 5, 9));
        bookshelves[3] = new Bookshelf(new Book("Совершенный код", "Стив Макконел", 2016), 66.45, new GregorianCalendar(2019, 11, 13));
        bookshelves[4] = new Bookshelf(new Book("Гибкая разработка программ на Java и C++", "Роберт Мартин", 2019), 109.17, new GregorianCalendar(2020, 2, 28));
        bookshelves[5] = new Bookshelf(new Book("Идеальный программист", "Роберт Мартин", 2020), 23.72, new GregorianCalendar(2020, 2, 3));
        bookshelves[6] = new Bookshelf(new Book("Дневник домового", "Евгений ЧеширКо", 2019), 24.05, new GregorianCalendar(2019, 1, 16));
        bookshelves[7] = new Bookshelf(new Book("Точка обмана", "Дэн Браун", 2014), 32.72, new GregorianCalendar(2018, 7, 7));
        bookshelves[8] = new Bookshelf(new Book("Код да Винчи", "Дэн Браун", 2013), 32.57, new GregorianCalendar(2018, 7, 7));
        bookshelves[9] = new Bookshelf(new Book("Цифровая крепость", "Дэн Браун", 2014), 32.72, new GregorianCalendar(2018, 7, 7));
        return bookshelves;
    }
}
