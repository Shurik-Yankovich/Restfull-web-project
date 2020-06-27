package bookstore.util;

import bookstore.model.Bookshelf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static bookstore.model.book.BookFactory.*;

public class BookGenerator {
    public static List<Bookshelf> generate(){
        List<Bookshelf> bookshelves = new ArrayList<>();
        bookshelves.add(new Bookshelf(NOVEL, "Алхимик", "Пауло Коэльо", 2019, 3, 14.22, LocalDate.of(2020, 1, 20)));
        bookshelves.add(new Bookshelf(NOVEL,"Шантарам", "Грэгори Дэвид Робертс", 2013, 2, 29.02, LocalDate.of(2020, 3, 21)));
        bookshelves.add(new Bookshelf(TECHNICAL, "Чистый код", "Роберт Мартин", 2016, 1, 40.17, LocalDate.of(2020, 5, 9)));
        bookshelves.add(new Bookshelf(TECHNICAL,"Совершенный код", "Стив Макконнелл", 2016, 5, 66.45, LocalDate.of(2019, 11, 13)));
        bookshelves.add(new Bookshelf(TECHNICAL,"Гибкая разработка программ на Java и C ++", "Роберт Мартин", 2019, 4, 109.17, LocalDate.of(2020, 2, 28)));
        bookshelves.add(new Bookshelf(TECHNICAL,"Идеальный программист", "Роберт Мартин", 2020, 1, 23.72, LocalDate.of(2020, 2, 3)));
        bookshelves.add(new Bookshelf(NOVEL,"Дневник домового", "Евгений ЧеширКо", 2019, 10, 24.05, LocalDate.of(2019, 1, 16)));
        bookshelves.add(new Bookshelf(THRILLER, "Точка обмана", "Дэн Браун", 2014, 5, 32.72, LocalDate.of(2018, 7, 7)));
        bookshelves.add(new Bookshelf(THRILLER,"Код да Винчи", "Дэн Браун", 2013, 5, 32.57, LocalDate.of(2018, 7, 7)));
        bookshelves.add(new Bookshelf(THRILLER,"Цифровая крепость", "Дэн Браун", 2014, 5, 32.72, LocalDate.of(2018, 7, 7)));
        return bookshelves;
    }
}
