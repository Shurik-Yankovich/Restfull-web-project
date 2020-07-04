package bookstore.util.csv;

import java.util.List;

public interface CsvUtil <T> {

    void writeInCsv(T t);
    void writeAllInCsv(List<T> t);
    T readFromCsv(int id);
    List<T> readAllFromCsv();
}
