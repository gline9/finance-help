package com.gline.finance.account;

import java.util.List;

public interface AccountStore
{
    List<Account> getAccounts();

    Account createAccount(AccountCreationRequest account);

    Account deleteAccount(AccountDeletionRequest account);
}
