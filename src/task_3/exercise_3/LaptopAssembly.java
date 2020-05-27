package task_3.exercise_3;

import task_3.exercise_3.line_step.CreateFirstPart;
import task_3.exercise_3.line_step.CreateSecondPart;
import task_3.exercise_3.line_step.CreateThirdPart;
import task_3.exercise_3.product.Product;
import task_3.exercise_3.product_part.ProductPart;

public class LaptopAssembly implements AssemblyLine {

    ProductPart[] productParts = new ProductPart[3];

    public LaptopAssembly(CreateFirstPart firstPart, CreateSecondPart secondPart, CreateThirdPart thirdPart) {
        productParts[0] = firstPart.buildProductPart();
        productParts[1] = secondPart.buildProductPart();
        productParts[2] = thirdPart.buildProductPart();
    }

    @Override
    public Product assembleProduct(Product product) {
        product.installFirstPart(productParts[0]);
        product.installSecondPart(productParts[1]);
        product.installThirdPart(productParts[2]);
        System.out.println("Ноутбук собран!");
        return product;
    }
}
