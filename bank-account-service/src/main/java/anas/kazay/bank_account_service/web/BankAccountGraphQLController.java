package anas.kazay.bank_account_service.web;

import anas.kazay.bank_account_service.entities.BankAccount;
import anas.kazay.bank_account_service.repositories.BankAccountRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BankAccountGraphQLController {
    private BankAccountRepository bankAccountRepository;
    public BankAccountGraphQLController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @QueryMapping
    public List<BankAccount> accountsList(){
        return bankAccountRepository.findAll();
    }
    @QueryMapping
    public BankAccount bankAccountById(String id){
        return bankAccountRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Bank account not found")
        );
    }
}
