package com.gline.finance;

import com.gline.finance.serialization.Memento;
import com.gline.finance.serialization.Serializable;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import java.util.List;

import static ratpack.jackson.Jackson.json;

public interface CRUDHandler<P, D, T extends Serializable> extends Handler
{

    @Override
    default void handle(Context ctx) throws Exception
    {
        ctx.byMethod(subCtx -> subCtx
                .get(getCtx -> getCtx.render(json(this.getAll())))
                .post(putCtx -> putCtx.parse(Memento.class).then(data -> {
                    T ret = post(makePostObject(data));
                    putCtx.render(json(ret));
                }))
                .delete(deleteCtx -> deleteCtx.parse(Memento.class).then(data -> {
                    T ret = delete(makeDeleteObject(data));
                    deleteCtx.render(json(ret));
                })));
    }

    List<T> getAll();

    T post(P value);

    P makePostObject(Memento memento);

    T delete(D value);

    D makeDeleteObject(Memento memento);


}
