package task_3.exercise_2.product.fruits;

import task_3.exercise_2.product.Product;

public class Orange extends Product {

    private String name;

    public Orange(double weight) {
        super(weight);
        name = "Апельсин";
    }

    @Override
    public String getName() {
        return name;
    }
}
