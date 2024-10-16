package anas.kazay.comptecqrseventsourcing.commands.aggregates;

import anas.kazay.comptecqrseventsourcing.common_api.commands.CreateAccountCommand;
import anas.kazay.comptecqrseventsourcing.common_api.commands.CreditAccountCommand;
import anas.kazay.comptecqrseventsourcing.common_api.commands.DebitAccountCommand;
import anas.kazay.comptecqrseventsourcing.common_api.enums.AccountStatus;
import anas.kazay.comptecqrseventsourcing.common_api.events.AccountActivatedEvent;
import anas.kazay.comptecqrseventsourcing.common_api.events.AccountCreatedEvent;
import anas.kazay.comptecqrseventsourcing.common_api.events.AccountCreditedEvent;
import anas.kazay.comptecqrseventsourcing.common_api.events.AccountDebitedEvent;
import anas.kazay.comptecqrseventsourcing.common_api.exceptions.BalanceNotSufficientException;
import anas.kazay.comptecqrseventsourcing.common_api.exceptions.NegativeAmmountException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        if(createAccountCommand.getIntialBalance() < 0) {
            throw new IllegalArgumentException("Initial balance cannot be less than 0");
        }
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getIntialBalance(),
                createAccountCommand.getCurrency(),
                AccountStatus.CREATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountId = event.getId();
        this.balance = event.getIntialBalance();
        this.currency = event.getCurrency();
        this.status = AccountStatus.CREATED;
        AggregateLifecycle.apply(
                new AccountActivatedEvent(
                        event.getId(),
                        AccountStatus.ACTIVATED
                )
        );
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand) {
       if (creditAccountCommand.getAmount() < 0) {
           throw new NegativeAmmountException("Credit amount cannot be less than 0");
       }
       AggregateLifecycle.apply(
               new AccountCreditedEvent(
                       creditAccountCommand.getId(),
                       creditAccountCommand.getAmount(),
                       creditAccountCommand.getCurrency()
       ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand) {
        if (debitAccountCommand.getAmount() < 0) {
            throw new NegativeAmmountException("Debit amount cannot be less than 0");
        }
        if (this.balance - debitAccountCommand.getAmount() < 0) {
            throw new BalanceNotSufficientException("Insufficient funds");
        }
        AggregateLifecycle.apply(
                new AccountDebitedEvent(
                        debitAccountCommand.getId(),
                        debitAccountCommand.getAmount(),
                        debitAccountCommand.getCurrency()
                )
        );
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        this.balance -= event.getAmount();
    }


}
