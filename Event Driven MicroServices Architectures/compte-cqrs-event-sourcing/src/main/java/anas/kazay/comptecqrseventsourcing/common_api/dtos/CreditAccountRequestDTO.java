package anas.kazay.comptecqrseventsourcing.common_api.dtos;

import lombok.Data;

@Data
public class CreditAccountRequestDTO {
    private String accountId;
    private double amount;
    private String currency;
}
