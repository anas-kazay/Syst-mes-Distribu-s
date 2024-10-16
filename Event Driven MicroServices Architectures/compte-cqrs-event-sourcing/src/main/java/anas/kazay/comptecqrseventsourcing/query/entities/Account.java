package anas.kazay.comptecqrseventsourcing.query.entities;

import anas.kazay.comptecqrseventsourcing.common_api.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class Account {
    @Id
    private String id;
    private double balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @OneToMany(mappedBy = "account")
    private Collection<Operation> operations;
}
