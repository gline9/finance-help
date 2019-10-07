package com.gline.finance.persistence;

import com.gline.finance.serialization.Memento;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class PersistenceHandler implements Handler
{
    private final PersistenceManager persistenceManager;

    public PersistenceHandler(PersistenceManager persistenceManager)
    {
        this.persistenceManager = persistenceManager;
    }

    @Override
    public void handle(Context ctx) throws Exception
    {
        ctx.byMethod(subCtx -> subCtx
            .post(postCtx -> postCtx.parse(Memento.class).then(data -> {
                PersistenceCommand.fromMemento(data).execute(persistenceManager);
                postCtx.render("{\"success\": true}");
            })));
    }
}
