package task_3.exercise_3.product;

import task_3.exercise_3.product_part.LaptopCase;
import task_3.exercise_3.product_part.Monitor;
import task_3.exercise_3.product_part.Motherboard;
import task_3.exercise_3.product_part.ProductPart;

public class Laptop implements Product {
    private LaptopCase laptopCase;
    private Motherboard motherboard;
    private Monitor monitor;

    @Override
    public void installFirstPart(ProductPart firstProductPart) {
        if (firstProductPart instanceof LaptopCase) {
            laptopCase = (LaptopCase) firstProductPart;
            System.out.println("Установлен корпус ноутбука!");
        } else {
            System.out.println("Неверно подобрана деталь!");
        }
    }

    @Override
    public void installSecondPart(ProductPart secondProductPart) {
        if (secondProductPart instanceof Motherboard) {
            motherboard = (Motherboard) secondProductPart;
            System.out.println("Установлена материнская плата ноутбука!");
        } else {
            System.out.println("Неверно подобрана деталь!");
        }
    }

    @Override
    public void installThirdPart(ProductPart thirdProductPart) {
        if (thirdProductPart instanceof Monitor) {
            monitor = (Monitor) thirdProductPart;
            System.out.println("Установлен монитор ноутбука!");
        } else {
            System.out.println("Неверно подобрана деталь!");
        }
    }
}
