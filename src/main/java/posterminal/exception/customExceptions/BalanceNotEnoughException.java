package posterminal.exception.customExceptions;

public class BalanceNotEnoughException extends RuntimeException {
    public BalanceNotEnoughException(String message) {
        super(message);
    }
}
