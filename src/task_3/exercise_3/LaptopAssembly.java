package task_3.exercise_3;

import task_3.exercise_3.line_step.CreateFirstPart;
import task_3.exercise_3.line_step.CreateSecondPart;
import task_3.exercise_3.line_step.CreateThirdPart;
import task_3.exercise_3.line_step.LineStep;
import task_3.exercise_3.product.Product;

public class LaptopAssembly implements AssemblyLine {

    LineStep[] lineSteps = new LineStep[3];

    public LaptopAssembly() {
        lineSteps[0] = new CreateFirstPart();
        lineSteps[1] = new CreateSecondPart();
        lineSteps[2] = new CreateThirdPart();
    }

    @Override
    public Product assembleProduct(Product product) {
        product.installFirstPart(lineSteps[0].buildProductPart());
        product.installSecondPart(lineSteps[1].buildProductPart());
        product.installThirdPart(lineSteps[2].buildProductPart());
        System.out.println("Ноутбук собран!");
        return product;
    }
}
