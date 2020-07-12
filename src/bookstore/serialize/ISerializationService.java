package bookstore.serialize;

import java.util.List;

public interface ISerializationService <T>  {

    void save(List<T> list, String fileName);
    List<T> load(String fileName);
}
