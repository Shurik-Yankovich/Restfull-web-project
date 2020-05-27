package task_3.exercise_3.line_step;

import task_3.exercise_3.product_part.Monitor;
import task_3.exercise_3.product_part.ProductPart;

public class CreateThirdPart implements LineStep {
    @Override
    public ProductPart buildProductPart() {
        System.out.println("Мы изготовили монитор ноутбука!");
        return new Monitor();
    }
}
