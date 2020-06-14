package electronicbookstore.repository;

import electronicbookstore.model.Book;
import electronicbookstore.model.Bookshelf;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class BookGenerator {
    public static Bookshelf[] generate(){
        Bookshelf[] bookshelves = new Bookshelf[10];
        bookshelves[0] = new Bookshelf(new Book("Алхимик", "Пауло Коэльо", 2019), 14.22, new GregorianCalendar(2020, Calendar.JANUARY, 20));
        bookshelves[1] = new Bookshelf(new Book("Шантарам", "Грэгори Дэвид Робертс", 2013), 29.02, new GregorianCalendar(2020, Calendar.MARCH, 21));
        bookshelves[2] = new Bookshelf(new Book("Чистый код", "Роберт Мартин", 2016), 40.17, new GregorianCalendar(2020, Calendar.MAY, 9));
        bookshelves[3] = new Bookshelf(new Book("Совершенный код", "Стив Макконнелл", 2016), 66.45, new GregorianCalendar(2019, Calendar.NOVEMBER, 13));
        bookshelves[4] = new Bookshelf(new Book("Гибкая разработка программ на Java и C ++", "Роберт Мартин", 2019), 109.17, new GregorianCalendar(2020, Calendar.FEBRUARY, 28));
        bookshelves[5] = new Bookshelf(new Book("Идеальный программист", "Роберт Мартин", 2020), 23.72, new GregorianCalendar(2020, Calendar.FEBRUARY, 3));
        bookshelves[6] = new Bookshelf(new Book("Дневник домового", "Евгений ЧеширКо", 2019), 24.05, new GregorianCalendar(2019, Calendar.JANUARY, 16));
        bookshelves[7] = new Bookshelf(new Book("Точка обмана", "Дэн Браун", 2014), 32.72, new GregorianCalendar(2018, Calendar.JULY, 7));
        bookshelves[8] = new Bookshelf(new Book("Код да Винчи", "Дэн Браун", 2013), 32.57, new GregorianCalendar(2018, Calendar.JULY, 7));
        bookshelves[9] = new Bookshelf(new Book("Цифровая крепость", "Дэн Браунn", 2014), 32.72, new GregorianCalendar(2018, Calendar.JULY, 7));
        return bookshelves;
    }
}
