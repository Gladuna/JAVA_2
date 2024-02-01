import java.util.logging.Level;
import java.util.logging.Logger;

// Интерфейс стратегии для операций
interface OperationStrategy {
    ComplexNumber perform(ComplexNumber num1, ComplexNumber num2);
}

// Класс комплексного числа
class ComplexNumber {
    double real;
    double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    @Override
    public String toString() {
        return real + " + " + imaginary + "i";
    }
}

// Конкретные стратегии для сложения, умножения и деления
class AddStrategy implements OperationStrategy {
    public ComplexNumber perform(ComplexNumber num1, ComplexNumber num2) {
        return new ComplexNumber(num1.real + num2.real, num1.imaginary + num2.imaginary);
    }
}

class MultiplyStrategy implements OperationStrategy {
    public ComplexNumber perform(ComplexNumber num1, ComplexNumber num2) {
        double real = num1.real * num2.real - num1.imaginary * num2.imaginary;
        double imaginary = num1.real * num2.imaginary + num1.imaginary * num2.real;
        return new ComplexNumber(real, imaginary);
    }
}

class DivideStrategy implements OperationStrategy {
    public ComplexNumber perform(ComplexNumber num1, ComplexNumber num2) {
        if (num2.real != 0 || num2.imaginary != 0) {
            double denominator = num2.real * num2.real + num2.imaginary * num2.imaginary;
            double real = (num1.real * num2.real + num1.imaginary * num2.imaginary) / denominator;
            double imaginary = (num1.imaginary * num2.real - num1.real * num2.imaginary) / denominator;
            return new ComplexNumber(real, imaginary);
        } else {
            Logger.getLogger(Calculator.class.getName()).log(Level.SEVERE, "Ошибка: деление на ноль.");
            return null;
        }
    }
}

// Класс калькулятора
class Calculator {
    private OperationStrategy strategy;

    public Calculator(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    public ComplexNumber performOperation(ComplexNumber num1, ComplexNumber num2) {
        ComplexNumber result = strategy.perform(num1, num2);
        Logger.getLogger(Calculator.class.getName()).log(Level.INFO, "Результат операции: " + result);
        return result;
    }
}

// Пример использования
public class Main {
    public static void main(String[] args) {
        // Создание объектов стратегий
        OperationStrategy addStrategy = new AddStrategy();
        OperationStrategy multiplyStrategy = new MultiplyStrategy();
        OperationStrategy divideStrategy = new DivideStrategy();

        // Создание калькулятора с выбранной стратегией
        Calculator calculator = new Calculator(addStrategy);

        // Примеры операций
        ComplexNumber resultAdd = calculator.performOperation(new ComplexNumber(3, 4), new ComplexNumber(2, 1));
        ComplexNumber resultMultiply = calculator.performOperation(new ComplexNumber(3, 4), new ComplexNumber(2, 1));

        // Изменение стратегии и выполнение новой операции
        calculator.setStrategy(divideStrategy);
        ComplexNumber resultDivide = calculator.performOperation(new ComplexNumber(3, 4), new ComplexNumber(2, 1));
    }
}
