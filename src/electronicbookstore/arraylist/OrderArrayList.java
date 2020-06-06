package electronicbookstore.arraylist;

import java.util.Arrays;

public class OrderArrayList implements MyArrayList {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] array;
    private int length;

    public OrderArrayList(Object[] array) {
        this.array = array;
        this.length = array.length;
    }

    public OrderArrayList(int count) {
        this.array = new Object[count];
    }

    public OrderArrayList() {
        this.array = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    public void add(Object element) {
        if (length == array.length) {
            increaseArrayLength();
        }
        array[length] = element;
        length++;
    }

    private void increaseArrayLength() {
        array = Arrays.copyOf(array, array.length + DEFAULT_CAPACITY);
    }

    @Override
    public int size(){
        return length;
    }

    @Override
    public Object get(int index){
        if (index >=0 && index<length){
            return array[index];
        } else {
            return null;
        }
    }

}
