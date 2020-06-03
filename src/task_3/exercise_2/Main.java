package task_3.exercise_2;

//Написать программу содержащую иерархию товаров для склада.
//Заполнить склад до предела и высчитать вес хранимого товара.

import task_3.exercise_2.product.fruits.*;
import task_3.exercise_2.product.vegetables.Cucumber;
import task_3.exercise_2.product.vegetables.Potato;

import static task_3.exercise_2.NumberGenerator.getRandomDouble;

public class Main {

    public static void main(String[] args) {
        Storage storage = new Storage(7);

        storage.addProduct(new Cucumber(getRandomDouble(50)));
        storage.addProduct(new Potato(getRandomDouble(100)));
        storage.addProduct(new Orange(getRandomDouble(40)));
        storage.addProduct(new Blackberry(getRandomDouble(10)));
        storage.addProduct(new Lemon(getRandomDouble(20)));
        storage.addProduct(new Grapefruit(getRandomDouble(30)));
        storage.addProduct(new Strawberry(getRandomDouble(70)));

        System.out.println("На складе хранятся:");
        storage.printProductsInStorage();
        System.out.println(String.format("Общий вес продуктов в заполненном складе составляет: %.3f кг", storage.getWeightOfProducts()));
    }

}
