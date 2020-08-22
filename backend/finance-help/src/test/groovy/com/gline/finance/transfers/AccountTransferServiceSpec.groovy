package com.gline.finance.transfers

import com.gline.finance.account.Account
import com.gline.finance.account.AccountStore
import spock.lang.Specification

class AccountTransferServiceSpec extends Specification
{
    void "should execute transfers between accounts"()
    {
        given:

        def accountStore = Mock(AccountStore)
        def account1 = Account.newCompoundingAccount("foo", 12, 0, 1)
        def account2 = Account.newCompoundingAccount("bar", 20, 0, 1)

        accountStore.getAccountById(account1.id) >> account1;
        accountStore.getAccountById(account2.id) >> account2;

        def transferService = new AccountTransferService(accountStore)

        when:

        transferService.transferBetweenAccounts(account1.id, account2.id, 10d)

        then:

        2d == account1.balance
        30d == account2.balance
    }

    void "should allow depositing into an account"()
    {
        given:

        def accountStore = Mock(AccountStore)

        def account = Account.newCompoundingAccount("foo", 12, 0, 1)
        accountStore.getAccountById(account.id) >> account;

        def transferService = new AccountTransferService(accountStore)

        when:

        transferService.depositIntoAccount(account.id, 10d)

        then:

        22d == account.balance
    }

    void "should allow withdrawing from an account"()
    {
        given:

        def accountStore = Mock(AccountStore)

        def account = Account.newCompoundingAccount("foo", 12, 0, 1)
        accountStore.getAccountById(account.id) >> account;

        def transferService = new AccountTransferService(accountStore)

        when:

        transferService.withdrawFromAccount(account.id, 10d)

        then:

        2d == account.balance
    }
}
