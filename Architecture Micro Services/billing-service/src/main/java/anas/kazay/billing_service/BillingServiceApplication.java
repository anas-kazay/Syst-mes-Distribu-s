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

import java.util.Collection;
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
			var customer = customerRestClient.getCustomerById(1L);
			Bill bill1 = billingRepository.save(new Bill(null,new Date(),null,customer.getId(),null));
			PagedModel<Product> products = productItemRestClient.pageProducts(0, 20);
			products.forEach(p -> {
				ProductItem productItem = new ProductItem();
				productItem.setPrice(p.getPrice());
				productItem.setQuantity(1+ (int)(Math.random()*100));
				productItem.setBill(bill1);
				productItem.setProductID(p.getId());
				productItemRepository.save(productItem);
			});
		};
	}

}
