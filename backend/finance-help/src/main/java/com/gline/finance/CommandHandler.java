package com.gline.finance;

import com.gline.finance.serialization.Memento;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public interface CommandHandler<T> extends Handler
{
    @Override
    default void handle(Context ctx) throws Exception
    {
        ctx.byMethod(subCtx -> subCtx
            .post(postCtx -> postCtx.parse(Memento.class).then(data -> {
                T command = parseCommand(data);
                boolean success = handleCommand(command);
                postCtx.render("{\"success\": " + success + "}");
            })));
    }

    T parseCommand(Memento memento) throws Exception;

    boolean handleCommand(T command) throws Exception;
}
