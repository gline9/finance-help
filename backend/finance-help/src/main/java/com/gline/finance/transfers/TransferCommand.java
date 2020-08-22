package com.gline.finance.transfers;

import com.gline.finance.serialization.Converters;
import com.gline.finance.serialization.Memento;

public class TransferCommand
{
    private static final String FROM_FIELD = "from";
    private static final String TO_FIELD = "to";
    private static final String AMOUNT_FIELD = "amount";

    private final String fromID;
    private final String toID;
    private final double amount;

    private TransferCommand(String fromID, String toID, double amount)
    {
        this.fromID = fromID;
        this.toID = toID;
        this.amount = amount;
    }

    static TransferCommand fromMemento(Memento memento)
    {
        return new TransferCommand(
                memento.getValue(FROM_FIELD, Converters.identity(String.class)),
                memento.getValue(TO_FIELD, Converters.identity(String.class)),
                memento.getValue(AMOUNT_FIELD, Converters.numberDoubleConverter())
        );
    }

    public String getFromID()
    {
        return fromID;
    }

    public String getToID()
    {
        return toID;
    }

    public double getAmount()
    {
        return amount;
    }
}
