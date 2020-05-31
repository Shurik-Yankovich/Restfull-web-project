package task_3.exercise_2.product.vegetables;

import task_3.exercise_2.product.Product;

public class Potato extends Product {

    private String name;

    public Potato(double weight) {
        super(weight);
        name = "Картофель";
    }

    @Override
    public String getName() {
        return null;
    }
}
