package com.gline.finance;

import com.gline.finance.serialization.Memento;
import com.gline.finance.serialization.Serializable;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import java.util.List;

import static ratpack.jackson.Jackson.json;

public interface CRUDHandler<POST, T extends Serializable> extends Handler
{

    @Override
    default void handle(Context ctx) throws Exception
    {
        ctx.byMethod(subCtx -> subCtx
                .get(getCtx -> getCtx.render(json(this.getAll())))
                .post(putCtx -> putCtx.parse(Memento.class).then(data -> {
                    T ret = post(makePostObject(data));
                    putCtx.render(json(ret));
                })));
    }

    List<T> getAll();

    T post(POST value);

    POST makePostObject(Memento memento);


}
