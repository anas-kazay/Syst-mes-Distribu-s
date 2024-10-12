package anas.kazay.invetory_service;

import anas.kazay.invetory_service.entities.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class InvetoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvetoryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ProductRepository productRepository,
							RepositoryRestConfiguration restConfiguration) {
		restConfiguration.exposeIdsFor(Product.class);
		return args -> {
			productRepository.save(new Product(null, "laptop", 9000, 5));
			productRepository.save(new Product(null, "smartphone", 5000, 10));
			productRepository.save(new Product(null, "tablet", 3000, 15));
			productRepository.findAll().forEach(System.out::println);
		};
	}

}
