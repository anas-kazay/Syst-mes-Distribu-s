package anas.kazay.comptecqrseventsourcing.common_api.events;

import anas.kazay.comptecqrseventsourcing.common_api.enums.AccountStatus;
import lombok.Getter;

public class AccountActivatedEvent extends BaseEvent<String>{
    @Getter private AccountStatus status;
    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
