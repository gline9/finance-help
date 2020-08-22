package com.gline.finance.account;

import java.util.List;

public interface AccountStore
{
    List<Account> getAccounts();

    Account getAccountById(String id);

    Account createAccount(AccountCreationRequest account);

    Account deleteAccount(AccountDeletionRequest account);
}
