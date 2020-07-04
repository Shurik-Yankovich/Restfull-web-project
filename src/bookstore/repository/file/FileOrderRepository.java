package bookstore.repository.file;

import bookstore.model.Order;
import bookstore.model.Status;
import bookstore.repository.base.OrderRepository;

import java.io.*;
import java.util.List;

public class FileOrderRepository implements OrderRepository {

    private static final String ROOT_DIR_PATH = "";

    private File rootDirectory;

    public FileOrderRepository() {
        this.rootDirectory = new File(ROOT_DIR_PATH);
    }

    @Override
    public Order create(Order order) {
        return null;
    }

    @Override
    public Order update(Order order, Status status) {
        return null;
    }

    @Override
    public Order read(Integer integer) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public List<Order> readAll() {
        return null;
    }

    @Override
    public void createAll(List<Order> t) {

    }
}
