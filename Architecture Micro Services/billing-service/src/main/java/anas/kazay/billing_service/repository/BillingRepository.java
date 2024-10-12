package anas.kazay.billing_service.repository;

import anas.kazay.billing_service.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BillingRepository extends JpaRepository<Bill, Long> {
}
