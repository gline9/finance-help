package com.gline.finance.transfers;

import com.gline.finance.CommandHandler;
import com.gline.finance.serialization.Memento;

public class DepositHandler implements CommandHandler<DepositCommand>
{
    private final TransferService transferService;

    public DepositHandler(TransferService transferService)
    {
        this.transferService = transferService;
    }

    @Override
    public DepositCommand parseCommand(Memento memento)
    {
        return DepositCommand.fromMemento(memento);
    }

    @Override
    public boolean handleCommand(DepositCommand command)
    {
        transferService.depositIntoAccount(command.getId(), command.getAmount());
        return true;
    }
}
