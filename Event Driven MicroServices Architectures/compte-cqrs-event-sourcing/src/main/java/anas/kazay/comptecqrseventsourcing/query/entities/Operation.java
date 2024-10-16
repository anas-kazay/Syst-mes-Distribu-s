package anas.kazay.comptecqrseventsourcing.query.entities;

import anas.kazay.comptecqrseventsourcing.common_api.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    private Account account;
}
