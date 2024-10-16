package anas.kazay.comptecqrseventsourcing.query.service;

import anas.kazay.comptecqrseventsourcing.common_api.enums.OperationType;
import anas.kazay.comptecqrseventsourcing.common_api.events.AccountActivatedEvent;
import anas.kazay.comptecqrseventsourcing.common_api.events.AccountCreatedEvent;
import anas.kazay.comptecqrseventsourcing.common_api.events.AccountCreditedEvent;
import anas.kazay.comptecqrseventsourcing.common_api.events.AccountDebitedEvent;
import anas.kazay.comptecqrseventsourcing.query.entities.Account;
import anas.kazay.comptecqrseventsourcing.query.entities.Operation;
import anas.kazay.comptecqrseventsourcing.query.repository.AccountRepository;
import anas.kazay.comptecqrseventsourcing.query.repository.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.info("Handling AccountCreatedEvent: {}", event.getId());

        accountRepository.save(new Account(
                event.getId(),
                event.getIntialBalance(),
                event.getCurrency(),
                event.getStatus(),
                null
        ));
    }

    @EventHandler
    public void on(AccountActivatedEvent event) {
        log.info("Handling AccountActivatedEvent: {}", event.getId());

        Account account = accountRepository.findById(event.getId()).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event) {
        log.info("Handling AccountDebitedEvent: {}", event.getId());

        Account account = accountRepository.findById(event.getId()).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setCurrency(account.getCurrency());
        operation.setDate(event.getDate());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event) {
        log.info("Handling AccountCreditedEvent: {}", event.getId());

        Account account = accountRepository.findById(event.getId()).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setCurrency(account.getCurrency());
        operation.setDate(event.getDate());
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);

    }
}
