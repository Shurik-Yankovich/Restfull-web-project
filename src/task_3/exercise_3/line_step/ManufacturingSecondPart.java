package task_3.exercise_3.line_step;

import task_3.exercise_3.product_part.Motherboard;
import task_3.exercise_3.product_part.ProductPart;

public class ManufacturingSecondPart implements LineStep {
    @Override
    public ProductPart buildProductPart() {
        System.out.println("Мы изготовили материнскую плату ноутбука!");
        return new Motherboard();
    }
}
