package task_3.exercise_2.product.fruits;

import task_3.exercise_2.product.Product;

public class Grapefruit implements Product {

    private String name;
    private double weight;

    public Grapefruit(double weight) {
        this.weight = weight;
        name = "Грейпфрут";
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
