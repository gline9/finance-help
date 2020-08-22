package com.gline.finance.transfers;

import com.gline.finance.CommandHandler;
import com.gline.finance.serialization.Memento;

public class WithdrawalHandler implements CommandHandler<WithdrawalCommand>
{
    private final TransferService transferService;

    public WithdrawalHandler(TransferService transferService)
    {
        this.transferService = transferService;
    }

    @Override
    public WithdrawalCommand parseCommand(Memento memento)
    {
        return WithdrawalCommand.fromMemento(memento);
    }

    @Override
    public boolean handleCommand(WithdrawalCommand command)
    {
        transferService.withdrawFromAccount(command.getId(), command.getAmount());
        return true;
    }
}
