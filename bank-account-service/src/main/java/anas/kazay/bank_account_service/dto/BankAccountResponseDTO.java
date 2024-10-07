package anas.kazay.bank_account_service.dto;

import anas.kazay.bank_account_service.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BankAccountResponseDTO {
    private String id;
    private double balance;
    private String currency;
    private AccountType accountType;
    private Date createdAt;

}
