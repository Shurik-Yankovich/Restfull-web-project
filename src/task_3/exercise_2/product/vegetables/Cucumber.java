package task_3.exercise_2.product.vegetables;

import task_3.exercise_2.product.Product;

public class Cucumber implements Product {

    private String name;
    private double weight;

    public Cucumber(double weight) {
        this.weight = weight;
        name = "Огурец";
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Продукт: [%s] имеет вес: %.3f кг", getName(), getWeight());
    }
}
