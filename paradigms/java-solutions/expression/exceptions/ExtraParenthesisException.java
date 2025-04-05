package expression.exceptions;

public class ExtraParenthesisException extends RuntimeException {
    public ExtraParenthesisException(String message) {
        super(message);
    }
}