package task_3.exercise_3;

import task_3.exercise_3.line_step.ManufacturingFirstPart;
import task_3.exercise_3.line_step.ManufacturingSecondPart;
import task_3.exercise_3.line_step.ManufacturingThirdPart;
import task_3.exercise_3.product.Laptop;
import task_3.exercise_3.product.Product;

public class Main {

    public static void main(String[] args) {
        AssemblyLine assemblyLine = new LaptopAssembly(new ManufacturingFirstPart(), new ManufacturingSecondPart(), new ManufacturingThirdPart());
        Product laptop = assemblyLine.assembleProduct(new Laptop());
    }
}
