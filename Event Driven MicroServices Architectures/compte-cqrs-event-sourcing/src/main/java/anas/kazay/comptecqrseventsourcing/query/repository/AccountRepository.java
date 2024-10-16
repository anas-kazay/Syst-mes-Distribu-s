package anas.kazay.comptecqrseventsourcing.query.repository;

import anas.kazay.comptecqrseventsourcing.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String>{
}
