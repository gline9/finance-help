package com.gline.finance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gline.finance.account.AccountHandler;
import com.gline.finance.account.AccountStore;
import com.gline.finance.account.InMemoryAccountStore;
import com.gline.finance.serialization.*;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.server.RatpackServer;

public class Main
{
    public static void main(String[] args) throws Exception
    {

        AccountStore accountStore = new InMemoryAccountStore();
        TotalBalanceStore totalBalanceStore = new AggregatingTotalBalanceStore(accountStore);

        initializeRatpackServer(chain -> chain
            .get(ctx -> ctx.render(""))
            .path("total/balance", new TotalBalanceHandler(totalBalanceStore))
            .path("accounts", new AccountHandler(accountStore)));

    }

    private static ObjectMapper getObjectMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Momento.class, new MomentoJsonDeserializer());
        module.addSerializer(Serializable.class, new MomentoJsonSerializer());
        mapper.registerModule(module);

        return mapper;
    }

    private static void initializeRatpackServer(Action<? super Chain> action) throws Exception
    {
        RatpackServer.start(server -> server
                .registryOf(registry -> registry
                        .add(ObjectMapper.class, getObjectMapper()))
                .handlers(action)
                .serverConfig(config -> config.port(5150))
        );
    }
}
