package anas.kazay.bank_account_service.web;

import anas.kazay.bank_account_service.dto.BankAccountRequestDTO;
import anas.kazay.bank_account_service.dto.BankAccountResponseDTO;
import anas.kazay.bank_account_service.entities.BankAccount;
import anas.kazay.bank_account_service.repositories.BankAccountRepository;
import anas.kazay.bank_account_service.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AccountRestController {
    private BankAccountRepository bankAccountRepository;
    private AccountService accountService;

    public AccountRestController(BankAccountRepository bankAccountRepository, AccountService accountService) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountService = accountService;
    }

    @GetMapping("/bankAccounts")
    public List<BankAccount> bankAccounts(){
        return bankAccountRepository.findAll();
    }

    @GetMapping("/bankAccounts/{id}")
    public BankAccount bankAccount(@PathVariable String id){
        return bankAccountRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Bank account not found")
        );
    }

    @PostMapping("/bankAccounts")
    public BankAccountResponseDTO save(@RequestBody BankAccountRequestDTO bankAccount){
        return accountService.createAccount(bankAccount);
    }

    @PutMapping("/bankAccounts/{id}")
    public BankAccount update(@PathVariable String id, @RequestBody BankAccount bankAccount){
        BankAccount account = bankAccountRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Bank account not found")
        );
        if(bankAccount.getBalance() != 0)
        account.setBalance(bankAccount.getBalance());
        if(bankAccount.getCurrency() != null)
        account.setCurrency(bankAccount.getCurrency());
        if(bankAccount.getAccountType() != null)
        account.setAccountType(bankAccount.getAccountType());
        return bankAccountRepository.save(account);
    }

    @DeleteMapping("/bankAccounts/{id}")
    public void delete(@PathVariable String id){
        bankAccountRepository.deleteById(id);
    }

}
