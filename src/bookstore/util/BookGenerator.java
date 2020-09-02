package bookstore.util;

import bookstore.entity.Bookshelf;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.StorageRepository;
import bookstore.repository.list.BookStorageRepository;

import java.time.LocalDate;

public class BookGenerator {
    public static StorageRepository generate() throws RepositoryException {
        StorageRepository storageRepository = new BookStorageRepository();
        storageRepository.create(new Bookshelf("Алхимик", "Пауло Коэльо", 2019, 3, 14.22, LocalDate.of(2020, 1, 20)));
        storageRepository.create(new Bookshelf("Шантарам", "Грэгори Дэвид Робертс", 2013, 2, 29.02, LocalDate.of(2020, 3, 21)));
        storageRepository.create(new Bookshelf("Чистый код", "Роберт Мартин", 2016, 1, 40.17, LocalDate.of(2020, 5, 9)));
        storageRepository.create(new Bookshelf("Совершенный код", "Стив Макконнелл", 2016, 5, 66.45, LocalDate.of(2019, 11, 13)));
        storageRepository.create(new Bookshelf("Гибкая разработка программ на Java и C ++", "Роберт Мартин", 2019, 4, 109.17, LocalDate.of(2020, 2, 28)));
        storageRepository.create(new Bookshelf("Идеальный программист", "Роберт Мартин", 2020, 1, 23.72, LocalDate.of(2020, 2, 3)));
        storageRepository.create(new Bookshelf("Дневник домового", "Евгений ЧеширКо", 2019, 10, 24.05, LocalDate.of(2019, 1, 16)));
        storageRepository.create(new Bookshelf( "Точка обмана", "Дэн Браун", 2014, 5, 32.72, LocalDate.of(2018, 7, 7)));
        storageRepository.create(new Bookshelf("Код да Винчи", "Дэн Браун", 2013, 5, 32.57, LocalDate.of(2018, 7, 7)));
        storageRepository.create(new Bookshelf("Цифровая крепость", "Дэн Браун", 2014, 5, 32.72, LocalDate.of(2018, 7, 7)));
        return storageRepository;
    }
}
