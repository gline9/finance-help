package com.gline.finance.account;

import com.gline.finance.serialization.Momento;
import com.gline.finance.serialization.Converters;

public class AccountCreationRequest
{
    private static final String BALANCE_FIELD = "balance";
    private static final String RATE_FIELD = "rate";
    private static final String COMPOUND_FIELD = "compoundsPerYear";

    private final double balance;
    private final double rate;
    private final double compoundsPerYear;

    private AccountCreationRequest(Momento momento)
    {
        balance = momento.getValue(BALANCE_FIELD, Converters.numberDoubleConverter());
        rate = momento.getValue(RATE_FIELD, Converters.numberDoubleConverter(), () -> 0d);
        compoundsPerYear = momento.getValue(COMPOUND_FIELD, Converters.numberDoubleConverter(), () -> 1d);
    }

    public static AccountCreationRequest fromMomento(Momento momento)
    {
        return new AccountCreationRequest(momento);
    }

    public Account create()
    {
        return Account.newCompoundingAccount(balance, rate, compoundsPerYear);
    }
}
