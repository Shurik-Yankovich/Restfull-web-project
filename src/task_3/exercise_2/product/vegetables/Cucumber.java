package task_3.exercise_2.product.vegetables;

import task_3.exercise_2.product.Product;

public class Cucumber extends Product {

    private String name;

    public Cucumber(double weight) {
        super(weight);
        name = "Огурец";
    }

    @Override
    public String getName() {
        return name;
    }
}
