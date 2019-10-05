package com.gline.finance.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAccountStore implements AccountStore
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
}
