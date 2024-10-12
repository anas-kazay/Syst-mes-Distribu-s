package anas.kazay.billing_service.web;

import anas.kazay.billing_service.entities.Bill;
import anas.kazay.billing_service.feign.CustomerRestClient;
import anas.kazay.billing_service.feign.ProductItemRestClient;
import anas.kazay.billing_service.model.Customer;
import anas.kazay.billing_service.model.Product;
import anas.kazay.billing_service.repository.BillingRepository;
import anas.kazay.billing_service.repository.ProductItemRepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingRestController {
    private BillingRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient inventoryItemRestClient;

    public BillingRestController(BillingRepository billRepository,
                                 ProductItemRepository productItemRepository,
                                 CustomerRestClient customerRestClient,
                                 ProductItemRestClient inventoryItemRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.inventoryItemRestClient = inventoryItemRestClient;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable Long id) {
        Bill bill = billRepository.findById(id).get();
        Customer customer = customerRestClient.getCustomerById(bill.getId());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi -> {
            Product product = inventoryItemRestClient.getProductById(pi.getProductID());
            pi.setProduct(product);
        });
        return bill;
    }
}
