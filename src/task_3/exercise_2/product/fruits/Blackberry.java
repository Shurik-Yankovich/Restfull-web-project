package task_3.exercise_2.product.fruits;

import task_3.exercise_2.product.Product;

public class Blackberry extends Product {

    private String name;

    public Blackberry(double weight) {
        super(weight);
        name = "Ежевика";
    }

    @Override
    public String getName() {
        return name;
    }


}
