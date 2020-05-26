package task_3.exercise_2.product.fruits;

import task_3.exercise_2.product.Product;

public abstract class Citrus extends Fruits {

    private String name;

    public Citrus(String name, double weight) {
        super(weight);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
