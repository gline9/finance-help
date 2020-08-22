package com.gline.finance.persistence;

import com.gline.finance.CommandHandler;
import com.gline.finance.serialization.Memento;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class PersistenceHandler implements CommandHandler<PersistenceCommand>
{
    private final PersistenceManager persistenceManager;

    public PersistenceHandler(PersistenceManager persistenceManager)
    {
        this.persistenceManager = persistenceManager;
    }

    @Override
    public PersistenceCommand parseCommand(Memento memento)
    {
        return PersistenceCommand.fromMemento(memento);
    }

    @Override
    public boolean handleCommand(PersistenceCommand command) throws Exception
    {
        command.execute(persistenceManager);
        return true;
    }
}
