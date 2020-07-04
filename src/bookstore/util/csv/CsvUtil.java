package bookstore.util.csv;

import java.util.List;

public interface CsvUtil <T> {

    void writeToCsv(T t);
    void writeAllToCsv(List<T> t);
    T readFromCsv(int id);
    List<T> readAllFromCsv();
}
