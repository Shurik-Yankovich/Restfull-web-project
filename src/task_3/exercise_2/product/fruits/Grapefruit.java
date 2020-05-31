package task_3.exercise_2.product.fruits;

import task_3.exercise_2.product.Product;

public class Grapefruit extends Product {

    private String name;

    public Grapefruit(double weight) {
        super(weight);
        name = "Грейпфрут";
    }

    @Override
    public String getName() {
        return name;
    }
}
