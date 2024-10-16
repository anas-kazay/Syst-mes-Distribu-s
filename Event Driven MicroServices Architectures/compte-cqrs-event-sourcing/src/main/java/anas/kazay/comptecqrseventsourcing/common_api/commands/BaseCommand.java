package anas.kazay.comptecqrseventsourcing.common_api.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseCommand<T> {
    @TargetAggregateIdentifier
    @Getter
    private T id;
    BaseCommand(T id) {
        this.id = id;
    }

}
