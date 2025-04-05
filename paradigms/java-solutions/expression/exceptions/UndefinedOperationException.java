package expression.exceptions;

public class UndefinedOperationException extends RuntimeException {
    public UndefinedOperationException(String message) {
        super(message);
    }
}