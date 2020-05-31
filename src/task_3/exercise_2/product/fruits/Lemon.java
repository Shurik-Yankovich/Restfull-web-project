package task_3.exercise_2.product.fruits;

import task_3.exercise_2.product.Product;

public class Lemon extends Product {

    private String name;

    public Lemon(double weight) {
        super(weight);
        name = "Лимон";
    }

    @Override
    public String getName() {
        return name;
    }
}
