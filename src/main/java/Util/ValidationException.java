package Util;

// Исключение для проверки значений, которые не могут быть null или должны быть больше определенного значения
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}