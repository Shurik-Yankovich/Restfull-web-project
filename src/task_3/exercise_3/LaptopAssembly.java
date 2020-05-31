package task_3.exercise_3;

import task_3.exercise_3.line_step.ManufacturingFirstPart;
import task_3.exercise_3.line_step.ManufacturingSecondPart;
import task_3.exercise_3.line_step.ManufacturingThirdPart;
import task_3.exercise_3.line_step.LineStep;
import task_3.exercise_3.product.Product;

public class LaptopAssembly implements AssemblyLine {

    LineStep[] lineSteps;

    public LaptopAssembly(ManufacturingFirstPart firstPart, ManufacturingSecondPart secondPart, ManufacturingThirdPart thirdPart) {
        lineSteps = new LineStep[3];
        lineSteps[0] = firstPart;
        lineSteps[1] = secondPart;
        lineSteps[2] = thirdPart;
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
