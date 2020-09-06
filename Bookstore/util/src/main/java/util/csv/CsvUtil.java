package util.csv;

import java.io.IOException;
import java.util.List;

public interface CsvUtil <T> {

    void writeToCsv(T t) throws IOException;
    void writeAllToCsv(List<T> t) throws IOException;
    T readFromCsv(int id) throws IOException;
    List<T> readAllFromCsv() throws IOException;
}
