package anas.kazay.billing_service;

import anas.kazay.billing_service.entities.Bill;
import anas.kazay.billing_service.entities.ProductItem;
import anas.kazay.billing_service.feign.CustomerRestClient;
import anas.kazay.billing_service.feign.ProductItemRestClient;
import anas.kazay.billing_service.model.Customer;
import anas.kazay.billing_service.model.Product;
import anas.kazay.billing_service.repository.BillingRepository;
import anas.kazay.billing_service.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BillingRepository billingRepository,
							ProductItemRepository productItemRepository,
							CustomerRestClient customerRestClient,
							ProductItemRestClient productItemRestClient) {
		return args -> {
			// List of customer IDs
			Long[] customerIds = {1L, 2L, 3L};

			for (Long customerId : customerIds) {
				// Fetch customer by ID
				Customer customer = customerRestClient.getCustomerById(customerId);

				// Generate 4 bills for each customer
				for (int i = 0; i < 4; i++) {
					// Create a new bill
					Bill bill = new Bill(null, new Date(), null, customer.getId(), null);
					Bill savedBill = billingRepository.save(bill);

					// Fetch products to associate with the bill
					PagedModel<Product> products = productItemRestClient.pageProducts(0, 20);
					products.forEach(p -> {
						// Create a product item and associate it with the bill
						ProductItem productItem = new ProductItem();
						productItem.setPrice(p.getPrice());
						productItem.setQuantity(1 + (int)(Math.random() * 100));
						productItem.setBill(savedBill);
						productItem.setProductID(p.getId());
						productItemRepository.save(productItem);
					});
				}
			}
		};
	}
}
