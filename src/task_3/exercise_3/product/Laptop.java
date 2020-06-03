package task_3.exercise_3.product;

import task_3.exercise_3.product_part.ProductPart;

public class Laptop implements Product {

    private ProductPart firstProductParts;
    private ProductPart secondProductPart;
    private ProductPart thirdProductPart;

    @Override
    public void installFirstPart(ProductPart firstProductPart) {
        this.firstProductParts = firstProductPart;
        System.out.println("Установлен корпус ноутбука!");
    }

    @Override
    public void installSecondPart(ProductPart secondProductPart) {
        this.secondProductPart = secondProductPart;
        System.out.println("Установлена материнская плата ноутбука!");
    }

    @Override
    public void installThirdPart(ProductPart thirdProductPart) {
        this.thirdProductPart = thirdProductPart;
        System.out.println("Установлен монитор ноутбука!");
    }
}
