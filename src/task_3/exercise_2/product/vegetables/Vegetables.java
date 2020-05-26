package task_3.exercise_2.product.vegetables;

import task_3.exercise_2.product.Product;

public abstract class Vegetables extends Product {

    private String name;

    public Vegetables(String name, double weight) {
        super(weight);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
