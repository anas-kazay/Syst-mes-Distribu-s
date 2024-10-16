package anas.kazay.comptecqrseventsourcing.commands.controllers;

import anas.kazay.comptecqrseventsourcing.common_api.commands.CreateAccountCommand;
import anas.kazay.comptecqrseventsourcing.common_api.commands.CreditAccountCommand;
import anas.kazay.comptecqrseventsourcing.common_api.commands.DebitAccountCommand;
import anas.kazay.comptecqrseventsourcing.common_api.dtos.CreateAccountRequestDTO;
import anas.kazay.comptecqrseventsourcing.common_api.dtos.CreditAccountRequestDTO;
import anas.kazay.comptecqrseventsourcing.common_api.dtos.DebitAccountRequestDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("commands/accounts")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()));
        return commandResponse;
    }

    @PutMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(
                new CreditAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return commandResponse;
    }

    @PutMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(
                new DebitAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return commandResponse;
    }

    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(Exception e){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }

}
