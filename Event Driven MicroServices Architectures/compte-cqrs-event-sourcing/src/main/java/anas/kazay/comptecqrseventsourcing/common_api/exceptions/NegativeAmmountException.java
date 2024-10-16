package anas.kazay.comptecqrseventsourcing.common_api.exceptions;

public class NegativeAmmountException extends RuntimeException {
    public NegativeAmmountException(String s) {
        super(s);
    }
}
