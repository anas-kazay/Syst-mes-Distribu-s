package anas.kazay.billing_service.repository;

import anas.kazay.billing_service.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.Collection;

@RepositoryRestController
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
    public Collection<ProductItem> findByBillId(Long id);
}
