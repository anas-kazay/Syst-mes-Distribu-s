package anas.kazay.bank_account_service;

import anas.kazay.bank_account_service.entities.BankAccount;
import anas.kazay.bank_account_service.entities.Customer;
import anas.kazay.bank_account_service.enums.AccountType;
import anas.kazay.bank_account_service.repositories.BankAccountRepository;
import anas.kazay.bank_account_service.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BankAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAccountServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository) {
		return args -> {
			// Step 1: Save customers
			Stream.of("Mohamed", "Anas", "Yassine", "Omar", "Hassan", "Khalid", "Karim", "Said", "Najib", "Mehdi")
					.forEach(name -> {
						Customer customer = Customer.builder()
								.name(name)
								.build();
						customerRepository.save(customer); // Save each customer
					});

			// Step 2: Generate bank accounts for each saved customer
			customerRepository.findAll().forEach(customer -> {
				for (int i = 0; i < 10; i++) {
					BankAccount bankAccount = BankAccount.builder()
							.id(UUID.randomUUID().toString())
							.accountType(Math.random() > 0.5 ? AccountType.CURRENT_ACCOUNT : AccountType.SAVING_ACCOUNT)
							.balance(5000 + Math.random() * 10000)
							.createdAt(new Date())
							.currency("MAD")
							.customer(customer)
							.build();
					bankAccountRepository.save(bankAccount); // Save bank account for the customer
				}
			});
		};
	}



}
