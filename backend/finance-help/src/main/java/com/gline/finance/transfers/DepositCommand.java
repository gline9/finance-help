package com.gline.finance.transfers;

import com.gline.finance.serialization.Converters;
import com.gline.finance.serialization.Memento;

public class DepositCommand
{
    private static final String ID_FIELD = "id";
    private static final String AMOUNT_FIELD = "amount";

    private final String id;
    private final double amount;

    private DepositCommand(String id, double amount)
    {
        this.id = id;
        this.amount = amount;
    }

    static DepositCommand fromMemento(Memento memento)
    {
        return new DepositCommand(
                memento.getValue(ID_FIELD, Converters.identity(String.class)),
                memento.getValue(AMOUNT_FIELD, Converters.numberDoubleConverter())
        );
    }

    public String getId()
    {
        return id;
    }

    public double getAmount()
    {
        return amount;
    }
}
