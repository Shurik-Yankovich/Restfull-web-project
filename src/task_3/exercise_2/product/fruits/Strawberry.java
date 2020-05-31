package task_3.exercise_2.product.fruits;

import task_3.exercise_2.product.Product;

public class Strawberry extends Product {

    private String name;

    public Strawberry(double weight) {
        super(weight);
        name = "Клубника";
    }

    @Override
    public String getName() {
        return name;
    }
}
