package com.gline.finance.transfers;

import com.gline.finance.account.Account;
import com.gline.finance.account.AccountStore;

public class AccountTransferService implements TransferService
{
    private AccountStore accountStore;

    public AccountTransferService(AccountStore accountStore)
    {
        this.accountStore = accountStore;
    }

    @Override
    public void transferBetweenAccounts(String fromID, String toID, double value)
    {
        Account from = accountStore.getAccountById(fromID);
        Account to = accountStore.getAccountById(toID);

        from.setBalance(from.getBalance() - value);
        to.setBalance(to.getBalance() + value);
    }

    @Override
    public void depositIntoAccount(String id, double value)
    {
        Account account = accountStore.getAccountById(id);

        account.setBalance(account.getBalance() + value);
    }

    @Override
    public void withdrawFromAccount(String id, double value)
    {
        Account account = accountStore.getAccountById(id);

        account.setBalance(account.getBalance() - value);
    }
}
