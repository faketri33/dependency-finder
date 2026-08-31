package org.faketri.command;

public interface ICommandHandler<TCommand> {
    void handle(TCommand command);
}
