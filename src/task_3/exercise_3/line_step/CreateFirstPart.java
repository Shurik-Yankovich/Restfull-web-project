package task_3.exercise_3.line_step;

import task_3.exercise_3.product_part.LaptopCase;
import task_3.exercise_3.product_part.ProductPart;

public class CreateFirstPart implements LineStep{
    @Override
    public ProductPart buildProductPart() {
        System.out.println("Мы изготовили корпус ноутбука!");
        return new LaptopCase();
    }
}
