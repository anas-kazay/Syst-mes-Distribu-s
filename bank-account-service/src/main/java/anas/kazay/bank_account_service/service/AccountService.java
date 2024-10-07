package anas.kazay.bank_account_service.service;

import anas.kazay.bank_account_service.dto.BankAccountRequestDTO;
import anas.kazay.bank_account_service.dto.BankAccountResponseDTO;
import anas.kazay.bank_account_service.entities.BankAccount;

public interface AccountService {
     BankAccountResponseDTO createAccount(BankAccountRequestDTO bankAccountRequestDTO);

     BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO bankAccountRequestDTO);
}
