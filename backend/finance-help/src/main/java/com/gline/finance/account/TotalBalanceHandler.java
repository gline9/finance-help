package com.gline.finance.account;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class TotalBalanceHandler implements Handler
{
    private final TotalBalanceStore balanceStore;

    public TotalBalanceHandler(TotalBalanceStore balanceStore)
    {
        this.balanceStore = balanceStore;
    }

    @Override
    public void handle(Context ctx) throws Exception
    {
        ctx.byMethod(subCtx -> subCtx
            .get(this::get));
    }

    public void get(Context ctx) throws Exception
    {
        ctx.render(Double.toString(balanceStore.getTotalBalance()));
    }

}
