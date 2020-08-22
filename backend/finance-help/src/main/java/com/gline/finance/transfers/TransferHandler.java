package com.gline.finance.transfers;

import com.gline.finance.CommandHandler;
import com.gline.finance.serialization.Memento;

public class TransferHandler implements CommandHandler<TransferCommand>
{
    private TransferService transferService;

    public TransferHandler(TransferService transferService)
    {
        this.transferService = transferService;
    }

    @Override
    public TransferCommand parseCommand(Memento memento)
    {
        return TransferCommand.fromMemento(memento);
    }

    @Override
    public boolean handleCommand(TransferCommand command)
    {
        transferService.transferBetweenAccounts(command.getFromID(), command.getToID(), command.getAmount());
        return true;
    }
}
