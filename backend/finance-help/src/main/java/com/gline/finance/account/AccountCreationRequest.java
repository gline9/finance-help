package com.gline.finance.account;

import com.gline.finance.serialization.Memento;
import com.gline.finance.serialization.Converters;

public class AccountCreationRequest
{
    private static final String BALANCE_FIELD = "balance";
    private static final String RATE_FIELD = "rate";
    private static final String COMPOUND_FIELD = "compoundsPerYear";

    private final double balance;
    private final double rate;
    private final double compoundsPerYear;

    private AccountCreationRequest(Memento memento)
    {
        balance = memento.getValue(BALANCE_FIELD, Converters.numberDoubleConverter());
        rate = memento.getValue(RATE_FIELD, Converters.numberDoubleConverter(), () -> 0d);
        compoundsPerYear = memento.getValue(COMPOUND_FIELD, Converters.numberDoubleConverter(), () -> 1d);
    }

    public static AccountCreationRequest fromMomento(Memento memento)
    {
        return new AccountCreationRequest(memento);
    }

    public Account create()
    {
        return Account.newCompoundingAccount(balance, rate, compoundsPerYear);
    }
}
