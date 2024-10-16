package anas.kazay.comptecqrseventsourcing.common_api.exceptions;

public class BalanceNotSufficientException extends RuntimeException {
    public BalanceNotSufficientException(String insufficientFunds) {
        super(insufficientFunds);
    }
}
