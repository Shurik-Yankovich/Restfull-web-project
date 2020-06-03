package task_3.exercise_2;

import java.util.Random;

public class NumberGenerator {

    private static Random random = new Random(System.currentTimeMillis());

    public static double getRandomDouble(double maxValue){
        return maxValue * random.nextDouble() + 1;
    }
}
