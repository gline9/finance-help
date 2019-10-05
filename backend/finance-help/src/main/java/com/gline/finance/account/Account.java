package com.gline.finance.account;

import com.gline.finance.serialization.Momento;
import com.gline.finance.serialization.Converters;
import com.gline.finance.serialization.Serializable;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class Account implements Serializable
{
    private static final long SECONDS_IN_YEAR = Duration.ofDays(365).toSeconds();

    private static final String ID_FIELD = "id";
    private static final String BALANCE_FIELD = "balance";
    private static final String RATE_FIELD = "rate";
    private static final String COMPOUND_FIELD = "compoundsPerYear";
    private static final String LAST_COMPOUND_TIME = "lastCompoundTime";

    private double balance;
    private final String id;
    private final double rate;
    private final double compoundsPerYear;
    private Instant lastCompoundTime;

    private Account(double balance, String id, double rate, double compoundsPerYear, Instant lastCompoundTime)
    {
        this.balance = balance;
        this.id = id;
        this.rate = rate;
        this.compoundsPerYear = compoundsPerYear;
        this.lastCompoundTime = lastCompoundTime;
    }

    private Account(Momento momento)
    {
        balance = momento.getValue(BALANCE_FIELD, Converters.numberDoubleConverter());
        id = momento.getValue(ID_FIELD, Converters.identity(String.class), () -> UUID.randomUUID().toString());
        rate = momento.getValue(RATE_FIELD, Converters.numberDoubleConverter());
        compoundsPerYear = momento.getValue(COMPOUND_FIELD, Converters.numberDoubleConverter());
        lastCompoundTime = momento.getValue(LAST_COMPOUND_TIME, Converters.instantMillisConverter());
    }

    public static Account newBasicAccount(double balance)
    {
        return Account.newCompoundingAccount(balance, 0d, 1d);
    }

    public static Account newCompoundingAccount(double balance, double rate, double compoundsPerYear)
    {
        return new Account(balance, UUID.randomUUID().toString(), rate, compoundsPerYear, Instant.now());
    }

    public static Account fromMomento(Momento momento)
    {
        return new Account(momento);
    }

    public double getBalance()
    {
        makeBalanceCurrent();

        return balance;
    }

    public String getId()
    {
        return id;
    }

    public void makeBalanceCurrent()
    {
        Instant compoundTime = Instant.now();

        Duration compoundDuration = Duration.between(lastCompoundTime, compoundTime);
        double percentOfYear = (double)compoundDuration.toSeconds() / SECONDS_IN_YEAR;

        balance = balance * Math.pow(1 + rate / compoundsPerYear, compoundsPerYear * percentOfYear);
        lastCompoundTime = compoundTime;
    }

    @Override
    public Momento serializeToMomento()
    {
        Momento ret = Momento.emptyBean();

        ret.setProperty(BALANCE_FIELD, balance);
        ret.setProperty(ID_FIELD, id);
        ret.setProperty(RATE_FIELD, rate);
        ret.setProperty(COMPOUND_FIELD, compoundsPerYear);
        ret.setProperty(LAST_COMPOUND_TIME, lastCompoundTime.toEpochMilli());

        return ret;
    }
}
