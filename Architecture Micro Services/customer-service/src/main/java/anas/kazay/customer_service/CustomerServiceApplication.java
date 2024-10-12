package anas.kazay.customer_service;

import anas.kazay.customer_service.entities.Customer;
import anas.kazay.customer_service.entities.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							RepositoryRestConfiguration restConfiguration) {
		restConfiguration.exposeIdsFor(Customer.class);
		return args -> {
			customerRepository.save(new Customer(null, "anas", "anas@gmail.com"));
			customerRepository.save(new Customer(null, "kazay", "kazay@gmail.com"));
			customerRepository.save(new Customer(null, "mohamed", "mohamed@gmail.com"));
			customerRepository.findAll().forEach(System.out::println);
		};
	}

}
