package com.gline.finance;

import com.gline.finance.account.Account;
import com.gline.finance.account.AccountStore;

public class AggregatingTotalBalanceStore implements TotalBalanceStore
{
    private final AccountStore accountStore;

    public AggregatingTotalBalanceStore(AccountStore accountStore)
    {
        this.accountStore = accountStore;
    }

    @Override
    public double getTotalBalance()
    {
        return accountStore.getAccounts().stream().mapToDouble(Account::getBalance).sum();
    }
}
