package com.gline.finance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gline.finance.account.*;
import com.gline.finance.persistence.JsonPersistenceManager;
import com.gline.finance.persistence.PersistenceHandler;
import com.gline.finance.persistence.PersistenceManager;
import com.gline.finance.serialization.*;
import com.gline.finance.transfers.*;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.server.RatpackServer;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        PersistenceManager persistenceManager = new JsonPersistenceManager();
        AccountStore accountStore = persistenceManager.registerStore(new InMemoryAccountStore());
        TotalBalanceStore totalBalanceStore = new AggregatingTotalBalanceStore(accountStore);
        TransferService transferService = new AccountTransferService(accountStore);

        initializeRatpackServer(chain -> chain
            .get(ctx -> ctx.render(""))
            .path("total/balance", new TotalBalanceHandler(totalBalanceStore))
            .path("accounts", new AccountHandler(accountStore))
            .path("persist", new PersistenceHandler(persistenceManager))
            .path("transactions/deposit", new DepositHandler(transferService))
            .path("transactions/withdrawal", new WithdrawalHandler(transferService))
            .path("transactions/transfer", new TransferHandler(transferService)));
    }

    public static ObjectMapper getObjectMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Memento.class, new MementoJsonDeserializer());
        module.addSerializer(Serializable.class, new MementoJsonSerializer());
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
