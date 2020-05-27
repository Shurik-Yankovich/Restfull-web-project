package task_3.exercise_3;

import task_3.exercise_3.line_step.CreateFirstPart;
import task_3.exercise_3.line_step.CreateSecondPart;
import task_3.exercise_3.line_step.CreateThirdPart;
import task_3.exercise_3.product.Laptop;
import task_3.exercise_3.product.Product;

public class Main {

    public static void main(String[] args) {
        AssemblyLine assemblyLine = new LaptopAssembly(new CreateFirstPart(), new CreateSecondPart(), new CreateThirdPart());
        Product laptop = assemblyLine.assembleProduct(new Laptop());
    }
}
