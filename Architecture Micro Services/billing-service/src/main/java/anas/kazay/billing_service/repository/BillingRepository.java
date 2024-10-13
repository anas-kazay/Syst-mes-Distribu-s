package anas.kazay.billing_service.repository;

import anas.kazay.billing_service.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface BillingRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByCustomerID(@Param("customerId") Long id);
}
