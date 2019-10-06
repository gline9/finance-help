package com.gline.finance.account;

import com.gline.finance.persistence.PersistableStore;
import com.gline.finance.serialization.Memento;
import com.gline.finance.serialization.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryAccountStore implements AccountStore, PersistableStore
{
    private final Map<String, Account> accountMap = new HashMap<>();

    @Override
    public List<Account> getAccounts()
    {
        accountMap.values().forEach(Account::makeBalanceCurrent);
        return new ArrayList<>(accountMap.values());
    }

    @Override
    public Account createAccount(AccountCreationRequest request)
    {
        Account created = request.create();

        accountMap.put(created.getId(), created);

        return created;
    }

    @Override
    public String getRepoPersistenceName()
    {
        return "accounts";
    }

    @Override
    public void setFromMementos(List<Memento> mementos)
    {
        List<Account> accounts = mementos.stream().map(Account::fromMemento).collect(Collectors.toList());

        accountMap.clear();

        for (Account account : accounts)
        {
            accountMap.put(account.getId(), account);
        }
    }

    @Override
    public List<Serializable> getSerializableData()
    {
        return new ArrayList<>(accountMap.values());
    }
}
