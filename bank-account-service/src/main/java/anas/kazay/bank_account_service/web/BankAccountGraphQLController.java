package anas.kazay.bank_account_service.web;

import anas.kazay.bank_account_service.dto.BankAccountRequestDTO;
import anas.kazay.bank_account_service.dto.BankAccountResponseDTO;
import anas.kazay.bank_account_service.entities.BankAccount;
import anas.kazay.bank_account_service.entities.Customer;
import anas.kazay.bank_account_service.repositories.BankAccountRepository;
import anas.kazay.bank_account_service.repositories.CustomerRepository;
import anas.kazay.bank_account_service.service.AccountServiceImpl;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BankAccountGraphQLController {
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountServiceImpl accountService;

    public BankAccountGraphQLController(BankAccountRepository bankAccountRepository, AccountServiceImpl accountService, CustomerRepository customerRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountService = accountService;
        this.customerRepository = customerRepository;
    }

    @QueryMapping
    public List<BankAccount> accountsList(){
        return bankAccountRepository.findAll();
    }
    @QueryMapping
    public BankAccount bankAccountById(@Argument String id){
        return bankAccountRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Bank account not found")
        );
    }

    @MutationMapping
    public BankAccountResponseDTO addAccount(@Argument BankAccountRequestDTO bankAccount){
        return accountService.createAccount(bankAccount);
    }

    @MutationMapping
    public BankAccountResponseDTO updateAccount(@Argument String id, @Argument BankAccountRequestDTO bankAccount){
        return accountService.updateAccount(id, bankAccount);
    }

    @MutationMapping
    public Boolean deleteAccount(@Argument String id){
        bankAccountRepository.deleteById(id);
        return true;
    }

    @QueryMapping
    public List<Customer> customersList(){
        return customerRepository.findAll();
    }
}

