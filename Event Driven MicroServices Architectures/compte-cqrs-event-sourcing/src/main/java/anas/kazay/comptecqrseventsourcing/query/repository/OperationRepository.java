package anas.kazay.comptecqrseventsourcing.query.repository;

import anas.kazay.comptecqrseventsourcing.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, String> {
}
