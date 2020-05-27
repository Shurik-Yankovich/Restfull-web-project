package task_3.exercise_3.product;

import task_3.exercise_3.product_part.LaptopCase;
import task_3.exercise_3.product_part.Monitor;
import task_3.exercise_3.product_part.Motherboard;
import task_3.exercise_3.product_part.ProductPart;

public class Laptop implements Product {
    @Override
    public void installFirstPart(ProductPart firstProductPart) {
        if (firstProductPart instanceof LaptopCase) {
            System.out.println("Установлен корпус ноутбука!");
        } else {
            System.out.println("Неверно подобрана деталь!");
        }
    }

    @Override
    public void installSecondPart(ProductPart secondProductPart) {
        if (secondProductPart instanceof Motherboard) {
            System.out.println("Установлена материнская плата ноутбука!");
        } else {
            System.out.println("Неверно подобрана деталь!");
        }
    }

    @Override
    public void installThirdPart(ProductPart thirdProductPart) {
        if (thirdProductPart instanceof Monitor) {
            System.out.println("Установлен монитор ноутбука!");
        } else {
            System.out.println("Неверно подобрана деталь!");
        }
    }
}
