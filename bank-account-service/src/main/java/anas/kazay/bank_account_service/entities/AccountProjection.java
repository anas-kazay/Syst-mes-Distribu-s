package anas.kazay.bank_account_service.entities;

import anas.kazay.bank_account_service.enums.AccountType;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {BankAccount.class}, name = "accountProjection")
public interface AccountProjection {
    public String getId();
    public AccountType getAccountType();
}
