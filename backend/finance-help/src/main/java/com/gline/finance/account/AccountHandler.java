package com.gline.finance.account;

import com.gline.finance.CRUDHandler;
import com.gline.finance.serialization.Momento;

import java.util.List;

public class AccountHandler implements CRUDHandler<AccountCreationRequest, Account>
{
    private final AccountStore accountStore;

    public AccountHandler(AccountStore store)
    {
        this.accountStore = store;
    }

    @Override
    public List<Account> getAll()
    {
        return accountStore.getAccounts();
    }

    @Override
    public Account post(AccountCreationRequest request)
    {
        return accountStore.createAccount(request);
    }

    @Override
    public AccountCreationRequest makePostObject(Momento momento)
    {
        return AccountCreationRequest.fromMomento(momento);
    }
}
