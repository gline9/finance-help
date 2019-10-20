package com.gline.finance.account;

import com.gline.finance.serialization.Converters;
import com.gline.finance.serialization.Memento;

public class AccountDeletionRequest
{
    private static final String ID_FIELD = "id";

    private final String id;

    private AccountDeletionRequest(Memento memento)
    {
        id = memento.getValue(ID_FIELD, Converters.identity(String.class));
    }

    public static AccountDeletionRequest fromMemento(Memento memento)
    {
        return new AccountDeletionRequest(memento);
    }

    public String getId()
    {
        return id;
    }
}
