package com.gline.finance.transfers;

public interface TransferService
{
    void transferBetweenAccounts(String fromID, String toID, double value);

    void depositIntoAccount(String id, double value);

    void withdrawFromAccount(String id, double value);
}
