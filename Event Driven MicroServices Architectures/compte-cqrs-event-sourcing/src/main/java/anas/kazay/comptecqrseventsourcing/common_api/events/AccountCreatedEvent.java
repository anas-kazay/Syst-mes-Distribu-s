package anas.kazay.comptecqrseventsourcing.common_api.events;

import anas.kazay.comptecqrseventsourcing.common_api.enums.AccountStatus;
import lombok.Getter;

public class AccountCreatedEvent extends BaseEvent<String>{
    @Getter private double intialBalance;
    @Getter private String currency;
    @Getter private AccountStatus status;
    public AccountCreatedEvent(String id, double intialBalance, String currency, AccountStatus status) {
        super(id);
        this.intialBalance = intialBalance;
        this.currency = currency;
        this.status = status;
    }
}
