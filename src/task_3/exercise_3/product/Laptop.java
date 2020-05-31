package task_3.exercise_3.product;

import task_3.exercise_3.product_part.LaptopCase;
import task_3.exercise_3.product_part.Monitor;
import task_3.exercise_3.product_part.Motherboard;
import task_3.exercise_3.product_part.ProductPart;

public class Laptop implements Product {

    private ProductPart[] productParts;

    public Laptop() {
        productParts = new ProductPart[3];
    }

    @Override
    public void installFirstPart(ProductPart firstProductPart) {
        if (firstProductPart instanceof LaptopCase) {
            productParts[0] = firstProductPart;
            System.out.println("Установлен корпус ноутбука!");
        } else {
            System.out.println("Неверно подобрана деталь!");
        }
    }

    @Override
    public void installSecondPart(ProductPart secondProductPart) {
        if (secondProductPart instanceof Motherboard) {
            productParts[1] = secondProductPart;
            System.out.println("Установлена материнская плата ноутбука!");
        } else {
            System.out.println("Неверно подобрана деталь!");
        }
    }

    @Override
    public void installThirdPart(ProductPart thirdProductPart) {
        if (thirdProductPart instanceof Monitor) {
            productParts[2] = thirdProductPart;
            System.out.println("Установлен монитор ноутбука!");
        } else {
            System.out.println("Неверно подобрана деталь!");
        }
    }
}
