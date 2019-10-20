package com.gline.finance.account;

import com.gline.finance.serialization.Memento;
import com.gline.finance.serialization.Converters;

public class AccountCreationRequest
{
    private static final String BALANCE_FIELD = "balance";
    private static final String RATE_FIELD = "rate";
    private static final String COMPOUND_FIELD = "compoundsPerYear";
    private static final String NAME_FIELD = "name";

    private final double balance;
    private final double rate;
    private final double compoundsPerYear;
    private final String name;

    private AccountCreationRequest(Memento memento)
    {
        balance = memento.getValue(BALANCE_FIELD, Converters.numberDoubleConverter());
        rate = memento.getValue(RATE_FIELD, Converters.numberDoubleConverter(), () -> 0d);
        compoundsPerYear = memento.getValue(COMPOUND_FIELD, Converters.numberDoubleConverter(), () -> 1d);
        name = memento.getValue(NAME_FIELD, Converters.identity(String.class));
    }

    public static AccountCreationRequest fromMemento(Memento memento)
    {
        return new AccountCreationRequest(memento);
    }

    public Account create()
    {
        return Account.newCompoundingAccount(name, balance, rate, compoundsPerYear);
    }
}
