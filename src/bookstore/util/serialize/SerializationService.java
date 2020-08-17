package bookstore.util.serialize;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializationService <T extends Serializable> implements ISerializationService<T>{

    @Override
    public void save(List<T> list, String fileName) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            oos.writeObject(list);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<T> load(String fileName) {
        List<T> list = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)))
        {
            list = (ArrayList<T>)ois.readObject();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }
}
