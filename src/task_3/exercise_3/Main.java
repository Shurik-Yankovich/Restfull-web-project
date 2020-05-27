package task_3.exercise_3;

import task_3.exercise_3.product.Laptop;
import task_3.exercise_3.product.Product;

public class Main {

    public static void main(String[] args) {
        AssemblyLine assemblyLine = new LaptopAssembly();
        Product laptop = assemblyLine.assembleProduct(new Laptop());
    }
}
