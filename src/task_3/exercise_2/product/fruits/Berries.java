package task_3.exercise_2.product.fruits;

import task_3.exercise_2.product.Product;

public abstract class Berries extends Fruits {

    private String name;

    public Berries(String name, double weight) {
        super(weight);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
