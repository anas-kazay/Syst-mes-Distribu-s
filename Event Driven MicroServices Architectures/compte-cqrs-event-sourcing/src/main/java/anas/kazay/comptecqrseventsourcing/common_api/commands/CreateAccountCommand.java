package anas.kazay.comptecqrseventsourcing.common_api.commands;

import lombok.Data;
import lombok.Getter;


public class CreateAccountCommand extends BaseCommand<String>{
    @Getter
    private double intialBalance;
    @Getter
    private String currency;
    public CreateAccountCommand(String id, double intialBalance, String currency) {
        super(id);
        this.intialBalance = intialBalance;
        this.currency = currency;
    }
}
