package task_3.exercise_1;

public class WorkWithNumber {

    public void doWorkWithNumberAndPrintResult() {
        int number;
        int sumOfDigits;
        number = numberGenerator();
        sumOfDigits = sumOfDigitsOfNumber(number);
        printNumberAndHisSumOfDigits(number, sumOfDigits);
    }

    private int numberGenerator() {
        return new java.util.Random(System.currentTimeMillis()).nextInt(900) + 100;
    }

    private int sumOfDigitsOfNumber(int number) {
        int tensOfNumbers = number / 10;
        if (tensOfNumbers == 0) {
            return number;
        } else {
            return number % 10 + sumOfDigitsOfNumber(tensOfNumbers);
        }
    }

    private void printNumberAndHisSumOfDigits(int number, int sumOfDigits) {
        System.out.println(String.format("Полученное число равно: %d\nСумма цифр данного числа равна: %d", number, sumOfDigits));
    }
}

