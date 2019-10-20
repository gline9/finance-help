package com.gline.finance.account;

import com.gline.finance.CRUDHandler;
import com.gline.finance.serialization.Memento;

import java.util.List;

public class AccountHandler implements CRUDHandler<AccountCreationRequest, AccountDeletionRequest, Account>
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
    public AccountCreationRequest makePostObject(Memento memento)
    {
        return AccountCreationRequest.fromMemento(memento);
    }

    @Override
    public Account delete(AccountDeletionRequest value)
    {
        return accountStore.deleteAccount(value);
    }

    @Override
    public AccountDeletionRequest makeDeleteObject(Memento memento)
    {
        return AccountDeletionRequest.fromMemento(memento);
    }
}
