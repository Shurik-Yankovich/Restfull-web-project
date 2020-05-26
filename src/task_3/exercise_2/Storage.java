package task_3.exercise_2;

import task_3.exercise_2.product.Product;

public class Storage {

    private Product[] products;

    public Storage(int numberOfProducts) {
        this.products = new Product[numberOfProducts];
    }

    public boolean addProduct(Product product) {
        for (int i = 0; i < products.length; i++) {
            if (products[i] == null) {
                products[i] = product;
                return true;
            }
        }
        return false;
    }

    public double getWeightOfProductsInStorage() {
        double sum = 0;
        for (Product product : products) {
            sum += product.getWeight();
        }
        return sum;
    }

    public void printProductsInStorage() {
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
