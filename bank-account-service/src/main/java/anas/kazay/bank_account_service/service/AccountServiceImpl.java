package anas.kazay.bank_account_service.service;

import anas.kazay.bank_account_service.dto.BankAccountRequestDTO;
import anas.kazay.bank_account_service.dto.BankAccountResponseDTO;
import anas.kazay.bank_account_service.entities.BankAccount;
import anas.kazay.bank_account_service.mappers.AccountMapper;
import anas.kazay.bank_account_service.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private BankAccountRepository bankAccountRepository;
    private AccountMapper accountMapper;

    public AccountServiceImpl(BankAccountRepository bankAccountRepository, AccountMapper accountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountMapper = accountMapper;
    }
    @Override
    public BankAccountResponseDTO createAccount(BankAccountRequestDTO bankAccountRequestDTO) {
        BankAccount bankAccount = BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .balance(bankAccountRequestDTO.getBalance())
                .accountType(bankAccountRequestDTO.getAccountType())
                .createdAt(new Date())
                .currency(bankAccountRequestDTO.getCurrency())
                .build();
        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        BankAccountResponseDTO bankAccountResponseDTO = accountMapper.fromBankAccount(savedAccount);
        return bankAccountResponseDTO;
    }


}
