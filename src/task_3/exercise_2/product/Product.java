package task_3.exercise_2.product;

public abstract class Product {

    private double weight;

    public Product(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public abstract String getName();

    @Override
    public String toString() {
        return String.format("Продукт: [%s] имеет вес: %.3f кг", getName(), getWeight());
    }
}
